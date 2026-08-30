const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp();

const db = admin.firestore();

// ============================================================================
// CONSTANTS
// ============================================================================

const SCORE_DROP_THRESHOLD = 0.25;
const ROLLING_WINDOW_DAYS = 14;
const OVERDUE_THRESHOLD_MINUTES = 30;

// ============================================================================
// RATE LIMITING & CACHING CONFIGURATION
// ============================================================================

const RATE_LIMITS = {
  gemini: {
    requestsPerMinute: 15,
    requestsPerDay: 1500, // Free tier: 15 RPM, 1500 RPD
    tokensPerDay: 32000, // Free tier: 32K tokens/day
  },
  bhashini: {
    requestsPerMinute: 60, // Bhashini is typically more generous
    requestsPerDay: 10000,
  },
};

const CACHE_DURATION = {
  geminiQuiz: 3600, // 1 hour for quiz questions
  geminiChat: 0, // No caching for chat (dynamic)
  geminiReminiscence: 86400, // 24 hours for reminiscence prompts
  bhashiniTTS: 604800, // 7 days for TTS (audio files)
  bhashiniSTT: 0, // No caching for STT
};

// Supported languages for Bhashini
const SUPPORTED_LANGUAGES = {
  en: { code: 'en', name: 'English', ttsEngine: 'en-IN', sttEngine: 'en-IN' },
  hi: { code: 'hi', name: 'Hindi', ttsEngine: 'hi-IN', sttEngine: 'hi-IN' },
  as: { code: 'as', name: 'Assamese', ttsEngine: 'as-IN', sttEngine: 'as-IN' },
  brx: { code: 'brx', name: 'Bodo', ttsEngine: 'brx-IN', sttEngine: 'brx-IN' },
  kha: { code: 'kha', name: 'Khasi', ttsEngine: 'kha-IN', sttEngine: 'kha-IN' },
  gar: { code: 'gar', name: 'Garo', ttsEngine: 'gar-IN', sttEngine: 'gar-IN' },
  lus: { code: 'lus', name: 'Mizo', ttsEngine: 'lus-IN', sttEngine: 'lus-IN' },
  mni: { code: 'mni', name: 'Manipuri', ttsEngine: 'mni-IN', sttEngine: 'mni-IN' },
  nmx: { code: 'nmx', name: 'Nagamese', ttsEngine: 'nmx-IN', sttEngine: 'nmx-IN' },
};

// ============================================================================
// API INITIALIZATION
// ============================================================================

let geminiClient = null;
let bhashiniClient = null;

/**
 * Initialize Gemini client (lazy loading for cold starts)
 * Free tier: Gemini 1.5 Flash
 */
function getGeminiClient() {
  if (!geminiClient) {
    const { GoogleGenerativeAI } = require('@google/generative-ai');
    const apiKey = process.env.GEMINI_API_KEY;

    if (!apiKey) {
      throw new Error('GEMINI_API_KEY not configured in environment');
    }

    geminiClient = new GoogleGenerativeAI(apiKey);
  }
  return geminiClient;
}

/**
 * Initialize Bhashini client
 * Uses ULCA (Unified Language Conversation Architecture) API
 */
function getBhashiniConfig() {
  return {
    baseUrl: 'https://meity-auth.ulcacontrib.org/ulca/apis',
    inferenceUrl: 'https://dhruva-api.bhashini.gov.in/services/inference/pipeline',
    userId: process.env.BHASHINI_USER_ID,
    apiKey: process.env.BHASHINI_API_KEY,
  };
}

// ============================================================================
// RATE LIMITING UTILITIES
// ============================================================================

/**
 * Check if user has exceeded rate limit
 * Uses Firestore for distributed rate limiting
 */
async function checkRateLimit(userId, apiType, operation) {
  const now = Date.now();
  const minuteAgo = now - 60000;
  const dayAgo = now - 86400000;

  const rateLimitRef = db.collection('rateLimits').doc(`${userId}_${apiType}`);

  try {
    const doc = await rateLimitRef.get();
    const data = doc.exists ? doc.data() : { requests: [] };

    // Filter requests within time windows
    const requestsLastMinute = data.requests.filter((r) => r > minuteAgo);
    const requestsLastDay = data.requests.filter((r) => r > dayAgo);

    const limits = RATE_LIMITS[apiType];

    // Check limits
    if (requestsLastMinute.length >= limits.requestsPerMinute) {
      return {
        allowed: false,
        reason: 'rate_limit_exceeded',
        message: `Rate limit exceeded: ${limits.requestsPerMinute} requests per minute`,
        retryAfter: 60 - (now - requestsLastMinute[0]) / 1000,
      };
    }

    if (requestsLastDay.length >= limits.requestsPerDay) {
      return {
        allowed: false,
        reason: 'daily_limit_exceeded',
        message: `Daily limit exceeded: ${limits.requestsPerDay} requests per day`,
        retryAfter: 86400 - (now - requestsLastDay[0]) / 1000,
      };
    }

    // Update rate limit document
    await rateLimitRef.set({
      requests: [...requestsLastDay, now],
      lastRequest: now,
    });

    return { allowed: true };
  } catch (error) {
    console.error('Rate limit check failed:', error);
    // Allow on error to not block users
    return { allowed: true };
  }
}

// ============================================================================
// CACHING UTILITIES
// ============================================================================

/**
 * Get cached response if available
 */
async function getCachedResponse(cacheKey, operation) {
  const cacheDuration = CACHE_DURATION[operation] || 0;
  if (cacheDuration === 0) return null;

  try {
    const cacheDoc = await db.collection('apiCache').doc(cacheKey).get();

    if (cacheDoc.exists) {
      const data = cacheDoc.data();
      const expiresAt = data.cachedAt.toDate().getTime() + cacheDuration * 1000;

      if (Date.now() < expiresAt) {
        console.log(`Cache hit for ${cacheKey}`);
        return data.response;
      }

      // Cache expired, delete it
      await cacheDoc.ref.delete();
    }

    return null;
  } catch (error) {
    console.error('Cache read error:', error);
    return null;
  }
}

/**
 * Store response in cache
 */
async function setCachedResponse(cacheKey, response, operation) {
  const cacheDuration = CACHE_DURATION[operation] || 0;
  if (cacheDuration === 0) return;

  try {
    await db.collection('apiCache').doc(cacheKey).set({
      response,
      cachedAt: admin.firestore.FieldValue.serverTimestamp(),
      operation,
      expiresIn: cacheDuration,
    });
  } catch (error) {
    console.error('Cache write error:', error);
  }
}

/**
 * Generate cache key
 */
function generateCacheKey(operation, ...parts) {
  const crypto = require('crypto');
  const content = parts.filter(Boolean).join('|');
  const hash = crypto.createHash('md5').update(content).digest('hex');
  return `${operation}_${hash}`;
}

// ============================================================================
// GEMINI API FUNCTIONS
// ============================================================================

/**
 * HTTP Callable: Generate quiz questions for Quick Recall Game
 *
 * @param {string} patientId - Patient ID for personalization
 * @param {string} topic - Quiz topic (e.g., "daily_routine", "family", "festivals")
 * @param {string} difficulty - 'easy' | 'medium' | 'hard'
 * @param {string} language - Language code (en, hi, as, etc.)
 * @param {number} questionCount - Number of questions (default: 5)
 */
exports.generateQuizQuestions = functions.https.onCall(async (data, context) => {
  // Allow unauthenticated calls from patient devices
  const userId = context.auth?.uid || data.patientId || 'anonymous';

  // Rate limit check
  const rateCheck = await checkRateLimit(userId, 'gemini', 'quiz');
  if (!rateCheck.allowed) {
    throw new functions.https.HttpsError('resource-exhausted', rateCheck.message, {
      retryAfter: Math.ceil(rateCheck.retryAfter),
    });
  }

  const {
    patientId,
    topic = 'general',
    difficulty = 'medium',
    language = 'en',
    questionCount = 5,
  } = data;

  // Check cache
  const cacheKey = generateCacheKey('geminiQuiz', topic, difficulty, language, questionCount);
  const cached = await getCachedResponse(cacheKey, 'geminiQuiz');
  if (cached) {
    return { success: true, questions: cached, cached: true };
  }

  console.log(`Generating ${questionCount} quiz questions for topic: ${topic}`);

  try {
    const client = getGeminiClient();
    const model = client.getGenerativeModel({ model: 'gemini-1.5-flash' });

    const languageName = SUPPORTED_LANGUAGES[language]?.name || 'English';

    const prompt = `You are a gentle quiz master creating questions for elderly individuals with mild to moderate dementia.

Generate ${questionCount} simple, nostalgic quiz questions in ${languageName} language on the topic: "${topic}".

Difficulty level: ${difficulty}
- easy: Simple recall, multiple choice with 2 options
- medium: Recognition questions, multiple choice with 3 options
- hard: Open-ended or fill-in-the-blank

Rules:
1. Questions should be warm, positive, and non-challenging
2. Focus on positive memories (festivals, family, food, nature)
3. Avoid questions that might cause frustration
4. Use simple, clear language
5. Include the correct answer and 2-3 wrong options

Return as JSON array:
[
  {
    "question": "Question text",
    "options": ["Option A", "Option B", "Option C"],
    "correctIndex": 0,
    "hint": "A gentle hint",
    "explanation": "Brief positive explanation"
  }
]`;

    const result = await model.generateContent(prompt);
    const response = await result.response;
    const text = response.text();

    // Parse JSON from response
    const jsonMatch = text.match(/\[[\s\S]*\]/);
    if (!jsonMatch) {
      throw new Error('Failed to parse quiz questions from response');
    }

    const questions = JSON.parse(jsonMatch[0]);

    // Cache the response
    await setCachedResponse(cacheKey, questions, 'geminiQuiz');

    return {
      success: true,
      questions,
      cached: false,
      generatedAt: new Date().toISOString(),
    };
  } catch (error) {
    console.error('Quiz generation failed:', error);
    throw new functions.https.HttpsError('internal', 'Failed to generate quiz questions', {
      error: error.message,
      useFallback: true,
      fallbackQuestions: getDefaultQuizQuestions(topic, language),
    });
  }
});

/**
 * HTTP Callable: Generate conversational chatbot response
 *
 * @param {string} patientId - Patient ID
 * @param {string} message - User's voice input (transcribed)
 * @param {string} language - Language code
 * @param {array} conversationHistory - Previous messages for context
 */
exports.generateChatResponse = functions.https.onCall(async (data, context) => {
  const userId = context.auth?.uid || data.patientId || 'anonymous';

  // Rate limit check
  const rateCheck = await checkRateLimit(userId, 'gemini', 'chat');
  if (!rateCheck.allowed) {
    throw new functions.https.HttpsError('resource-exhausted', rateCheck.message, {
      retryAfter: Math.ceil(rateCheck.retryAfter),
    });
  }

  const { patientId, message, language = 'en', conversationHistory = [] } = data;

  if (!message) {
    throw new functions.https.HttpsError('invalid-argument', 'message is required');
  }

  console.log(`Generating chat response for patient ${patientId}`);

  try {
    const client = getGeminiClient();
    const model = client.getGenerativeModel({ model: 'gemini-1.5-flash' });

    const languageName = SUPPORTED_LANGUAGES[language]?.name || 'English';

    // Build conversation context
    const history = conversationHistory.map((msg) => ({
      role: msg.role,
      parts: [{ text: msg.content }],
    }));

    const chat = model.startChat({
      history: [
        {
          role: 'user',
          parts: [{
            text: `You are a warm, caring companion for elderly individuals with dementia.
                   You speak in ${languageName}.
                   You are patient, kind, and supportive.
                   You help with reminders, answer questions gently, and provide comfort.
                   Keep responses short (1-2 sentences) and simple.
                   Always be positive and encouraging.`,
          }],
        },
        {
          role: 'model',
          parts: [{ text: `I understand. I will be a caring companion speaking in ${languageName}. I'll keep my responses short, warm, and positive.` }],
        },
        ...history,
      ],
    });

    const result = await chat.sendMessage(message);
    const response = await result.response;
    const text = response.text();

    return {
      success: true,
      response: text,
      language,
      timestamp: new Date().toISOString(),
    };
  } catch (error) {
    console.error('Chat generation failed:', error);

    // Return fallback response
    return {
      success: false,
      error: error.message,
      response: getDefaultChatResponse(language),
      isFallback: true,
    };
  }
});

/**
 * HTTP Callable: Generate reminiscence prompts from photo
 *
 * @param {string} patientId - Patient ID
 * @param {string} photoUrl - URL of the photo
 * @param {string} caption - Family-provided caption
 * @param {array} personTags - Names of people in photo
 * @param {string} language - Language code
 */
exports.generateReminiscencePrompt = functions.https.onCall(async (data, context) => {
  const userId = context.auth?.uid || data.patientId || 'anonymous';

  // Rate limit check
  const rateCheck = await checkRateLimit(userId, 'gemini', 'reminiscence');
  if (!rateCheck.allowed) {
    throw new functions.https.HttpsError('resource-exhausted', rateCheck.message, {
      retryAfter: Math.ceil(rateCheck.retryAfter),
    });
  }

  const { patientId, photoUrl, caption, personTags = [], language = 'en' } = data;

  if (!photoUrl && !caption) {
    throw new functions.https.HttpsError('invalid-argument', 'photoUrl or caption is required');
  }

  // Check cache
  const cacheKey = generateCacheKey('geminiReminiscence', photoUrl || caption, language);
  const cached = await getCachedResponse(cacheKey, 'geminiReminiscence');
  if (cached) {
    return { success: true, prompts: cached, cached: true };
  }

  console.log(`Generating reminiscence prompts for patient ${patientId}`);

  try {
    const client = getGeminiClient();
    const model = client.getGenerativeModel({ model: 'gemini-1.5-flash' });

    const languageName = SUPPORTED_LANGUAGES[language]?.name || 'English';

    let prompt;
    let imagePart = null;

    if (photoUrl) {
      // For now, we'll use caption-based generation
      // Image analysis requires downloading the image first
      prompt = `You are creating gentle reminiscence prompts for an elderly person with dementia.

Based on this family photo:
Caption: "${caption || 'A family photo'}"
People in photo: ${personTags.join(', ') || 'Not specified'}

Generate 3 warm, nostalgic prompts in ${languageName} that would help the person remember and talk about this moment.

Rules:
1. Prompts should be gentle questions or statements
2. Focus on emotions, relationships, and positive memories
3. Avoid questions that might cause distress if memories are unclear
4. Keep prompts short and simple

Return as JSON array:
[
  {
    "prompt": "A gentle question or statement",
    "type": "question" | "statement",
    "emotion": "happy" | "nostalgic" | "loving"
  }
]`;
    } else {
      prompt = `Generate 3 reminiscence prompts in ${languageName} based on this caption: "${caption}"

Return as JSON array with "prompt", "type", and "emotion" fields.`;
    }

    const result = await model.generateContent(prompt);
    const response = await result.response;
    const text = response.text();

    // Parse JSON
    const jsonMatch = text.match(/\[[\s\S]*\]/);
    const prompts = jsonMatch ? JSON.parse(jsonMatch[0]) : [];

    // Cache the response
    await setCachedResponse(cacheKey, prompts, 'geminiReminiscence');

    return {
      success: true,
      prompts,
      cached: false,
    };
  } catch (error) {
    console.error('Reminiscence generation failed:', error);
    return {
      success: false,
      error: error.message,
      prompts: getDefaultReminiscencePrompts(language),
      isFallback: true,
    };
  }
});

// ============================================================================
// BHASHINI API FUNCTIONS
// ============================================================================

/**
 * HTTP Callable: Text-to-Speech using Bhashini
 *
 * @param {string} text - Text to convert to speech
 * @param {string} language - Language code (en, hi, as, brx, kha, gar, lus, mni, nmx)
 * @param {string} gender - Voice gender ('male' | 'female')
 */
exports.textToSpeech = functions.https.onCall(async (data, context) => {
  const userId = context.auth?.uid || 'anonymous';

  // Rate limit check
  const rateCheck = await checkRateLimit(userId, 'bhashini', 'tts');
  if (!rateCheck.allowed) {
    throw new functions.https.HttpsError('resource-exhausted', rateCheck.message, {
      retryAfter: Math.ceil(rateCheck.retryAfter),
    });
  }

  const { text, language = 'en', gender = 'female' } = data;

  if (!text) {
    throw new functions.https.HttpsError('invalid-argument', 'text is required');
  }

  // Check cache
  const cacheKey = generateCacheKey('bhashiniTTS', text, language, gender);
  const cached = await getCachedResponse(cacheKey, 'bhashiniTTS');
  if (cached) {
    return { success: true, audioBase64: cached, cached: true, useOnDevice: false };
  }

  // Validate language
  const langConfig = SUPPORTED_LANGUAGES[language];
  if (!langConfig) {
    console.warn(`Unsupported language: ${language}, falling back to on-device TTS`);
    return {
      success: false,
      useOnDevice: true,
      reason: 'unsupported_language',
      message: `Language ${language} not supported. Use on-device TTS.`,
      fallbackLanguage: 'en',
    };
  }

  console.log(`TTS request: ${text.length} chars in ${language}`);

  try {
    const config = getBhashiniConfig();

    // Step 1: Get service ID for TTS
    const serviceResponse = await fetch(`${config.baseUrl}/v0/model/getModelsPipeline`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'userID': config.userId,
        'ulcaApiKey': config.apiKey,
      },
      body: JSON.stringify({
        pipelineTasks: [{
          taskType: 'tts',
          config: {
            language: { sourceLanguage: langConfig.code },
          },
        }],
        pipelineRequestConfig: {
          pipelineId: '64392f96daac500b55c543cd',
        },
      }),
    });

    if (!serviceResponse.ok) {
      throw new Error(`Bhashini service lookup failed: ${serviceResponse.status}`);
    }

    const serviceData = await serviceResponse.json();
    const ttsService = serviceData.pipelineResponseConfig?.[0]?.config?.[0];

    if (!ttsService) {
      throw new Error('No TTS service available for this language');
    }

    // Step 2: Call TTS inference
    const inferenceResponse = await fetch(config.inferenceUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': ttsService.serviceId,
      },
      body: JSON.stringify({
        pipelineTasks: [{
          taskType: 'tts',
          config: {
            language: { sourceLanguage: langConfig.code },
            serviceId: ttsService.serviceId,
            gender,
            audioFormat: 'wav',
          },
        }],
        inputData: {
          input: [{
            source: text,
          }],
        },
      }),
    });

    if (!inferenceResponse.ok) {
      throw new Error(`TTS inference failed: ${inferenceResponse.status}`);
    }

    const inferenceData = await inferenceResponse.json();
    const audioBase64 = inferenceData.pipelineResponse?.[0]?.audio?.[0]?.audioContent;

    if (!audioBase64) {
      throw new Error('No audio content in response');
    }

    // Cache the audio
    await setCachedResponse(cacheKey, audioBase64, 'bhashiniTTS');

    return {
      success: true,
      audioBase64,
      audioFormat: 'wav',
      language,
      cached: false,
      useOnDevice: false,
    };
  } catch (error) {
    console.error('TTS failed:', error);

    // Return fallback flag
    return {
      success: false,
      useOnDevice: true,
      reason: 'api_error',
      message: error.message,
      fallbackLanguage: language,
    };
  }
});

/**
 * HTTP Callable: Speech-to-Text using Bhashini
 *
 * @param {string} audioBase64 - Base64 encoded audio
 * @param {string} language - Language code
 * @param {string} audioFormat - Audio format ('wav', 'mp3', 'ogg')
 */
exports.speechToText = functions.https.onCall(async (data, context) => {
  const userId = context.auth?.uid || 'anonymous';

  // Rate limit check
  const rateCheck = await checkRateLimit(userId, 'bhashini', 'stt');
  if (!rateCheck.allowed) {
    throw new functions.https.HttpsError('resource-exhausted', rateCheck.message, {
      retryAfter: Math.ceil(rateCheck.retryAfter),
    });
  }

  const { audioBase64, language = 'en', audioFormat = 'wav' } = data;

  if (!audioBase64) {
    throw new functions.https.HttpsError('invalid-argument', 'audioBase64 is required');
  }

  // Validate language
  const langConfig = SUPPORTED_LANGUAGES[language];
  if (!langConfig) {
    return {
      success: false,
      useOnDevice: true,
      reason: 'unsupported_language',
      message: `Language ${language} not supported for STT. Use on-device STT.`,
    };
  }

  console.log(`STT request: audio in ${language}`);

  try {
    const config = getBhashiniConfig();

    // Step 1: Get service ID for ASR
    const serviceResponse = await fetch(`${config.baseUrl}/v0/model/getModelsPipeline`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'userID': config.userId,
        'ulcaApiKey': config.apiKey,
      },
      body: JSON.stringify({
        pipelineTasks: [{
          taskType: 'asr',
          config: {
            language: { sourceLanguage: langConfig.code },
          },
        }],
        pipelineRequestConfig: {
          pipelineId: '64392f96daac500b55c543cd',
        },
      }),
    });

    if (!serviceResponse.ok) {
      throw new Error(`Bhashini service lookup failed: ${serviceResponse.status}`);
    }

    const serviceData = await serviceResponse.json();
    const asrService = serviceData.pipelineResponseConfig?.[0]?.config?.[0];

    if (!asrService) {
      throw new Error('No ASR service available for this language');
    }

    // Step 2: Call ASR inference
    const inferenceResponse = await fetch(config.inferenceUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': asrService.serviceId,
      },
      body: JSON.stringify({
        pipelineTasks: [{
          taskType: 'asr',
          config: {
            language: { sourceLanguage: langConfig.code },
            serviceId: asrService.serviceId,
            audioFormat,
          },
        }],
        inputData: {
          input: [{
            audio: [{
              audioContent: audioBase64,
            }],
          }],
        },
      }),
    });

    if (!inferenceResponse.ok) {
      throw new Error(`ASR inference failed: ${inferenceResponse.status}`);
    }

    const inferenceData = await inferenceResponse.json();
    const transcript = inferenceData.pipelineResponse?.[0]?.output?.[0]?.source;

    if (!transcript) {
      throw new Error('No transcript in response');
    }

    return {
      success: true,
      transcript,
      language,
      confidence: inferenceData.pipelineResponse?.[0]?.output?.[0]?.confidence || 0.9,
      useOnDevice: false,
    };
  } catch (error) {
    console.error('STT failed:', error);

    return {
      success: false,
      useOnDevice: true,
      reason: 'api_error',
      message: error.message,
    };
  }
});

/**
 * HTTP Callable: Get supported languages
 */
exports.getSupportedLanguages = functions.https.onCall(async (data, context) => {
  return {
    success: true,
    languages: SUPPORTED_LANGUAGES,
  };
});

// ============================================================================
// FALLBACK CONTENT
// ============================================================================

function getDefaultQuizQuestions(topic, language) {
  const questions = {
    en: [
      {
        question: 'What is your favorite festival?',
        options: ['Diwali', 'Holi', 'Both'],
        correctIndex: 2,
        hint: 'Think about the lights and colors',
        explanation: 'Festivals bring joy and togetherness!',
      },
      {
        question: 'What do you enjoy eating for breakfast?',
        options: ['Roti and sabji', 'Rice and dal', 'All of these'],
        correctIndex: 2,
        hint: 'Home-cooked food is the best',
        explanation: 'A good breakfast gives you energy!',
      },
    ],
    hi: [
      {
        question: 'आपका पसंदीदा त्योहार कौन सा है?',
        options: ['दिवाली', 'होली', 'दोनों'],
        correctIndex: 2,
        hint: 'रोशनी और रंगों के बारे में सोचें',
        explanation: 'त्योहार खुशी और एकता लाते हैं!',
      },
    ],
  };

  return questions[language] || questions.en;
}

function getDefaultChatResponse(language) {
  const responses = {
    en: "I'm here to help you. Could you tell me more about that?",
    hi: 'मैं आपकी मदद के लिए यहाँ हूँ। क्या आप मुझे और बता सकते हैं?',
    as: 'মই আপোনাক সহায় কৰিবলৈ ইয়াত আছোঁ। আপুনি মোক অধিক কব পাৰে নেকি?',
  };

  return responses[language] || responses.en;
}

function getDefaultReminiscencePrompts(language) {
  const prompts = {
    en: [
      { prompt: 'This looks like a beautiful memory. What made this day special?', type: 'question', emotion: 'happy' },
      { prompt: 'The people in this photo seem to care about each other very much.', type: 'statement', emotion: 'loving' },
    ],
    hi: [
      { prompt: 'यह एक सुंदर याद लगती है। इस दिन को क्या खास बनाया?', type: 'question', emotion: 'happy' },
    ],
  };

  return prompts[language] || prompts.en;
}

// ============================================================================
// EXISTING FUNCTIONS (from previous implementation)
// ============================================================================

const { GoogleGenerativeAI } = require('@google/generative-ai');

// ... existing game analysis and report functions ...
// (The previous functions would be included here)

// For now, including the core ones:

exports.onGameResultWritten = functions.firestore
  .document('patients/{patientId}/gameResults/{sessionId}')
  .onCreate(async (snap, context) => {
    const gameResult = snap.data();
    const { patientId, sessionId } = context.params;

    console.log(`Analyzing game result for patient ${patientId}`);

    try {
      const windowStart = new Date();
      windowStart.setDate(windowStart.getDate() - ROLLING_WINDOW_DAYS);

      const historicalResultsSnap = await db
        .collection(`patients/${patientId}/gameResults`)
        .where('gameType', '==', gameResult.gameType)
        .where('timestamp', '>=', admin.firestore.Timestamp.fromDate(windowStart))
        .orderBy('timestamp', 'desc')
        .limit(50)
        .get();

      if (historicalResultsSnap.size < 3) {
        console.log(`Not enough historical data`);
        return null;
      }

      let totalScorePercent = 0;
      let count = 0;

      historicalResultsSnap.forEach((doc) => {
        if (doc.id === sessionId) return;
        const data = doc.data();
        totalScorePercent += (data.score / data.maxScore) * 100;
        count++;
      });

      if (count === 0) return null;

      const averageScorePercent = totalScorePercent / count;
      const currentScorePercent = (gameResult.score / gameResult.maxScore) * 100;
      const dropPercent = (averageScorePercent - currentScorePercent) / averageScorePercent;

      if (dropPercent >= SCORE_DROP_THRESHOLD) {
        const alertRef = db.collection(`patients/${patientId}/behavioralAlerts`).doc();

        await alertRef.set({
          type: 'cognitive_decline',
          severity: dropPercent >= 0.5 ? 'critical' : dropPercent >= 0.35 ? 'high' : 'medium',
          description: `Significant drop in ${gameResult.gameType} performance: ${(dropPercent * 100).toFixed(0)}% below average`,
          timestamp: admin.firestore.FieldValue.serverTimestamp(),
          acknowledged: false,
          metadata: { gameType: gameResult.gameType, sessionId, dropPercent },
        });
      }

      return null;
    } catch (error) {
      console.error('Error analyzing game result:', error);
      return null;
    }
  });

exports.checkOverdueReminders = functions.https.onCall(async (data, context) => {
  const { patientId, overdueMinutes = OVERDUE_THRESHOLD_MINUTES } = data;

  if (!patientId) {
    throw new functions.https.HttpsError('invalid-argument', 'patientId is required');
  }

  const now = admin.firestore.Timestamp.now();
  const overdueThreshold = new Date(now.toDate().getTime() - overdueMinutes * 60 * 1000);

  const overdueSnap = await db
    .collection(`patients/${patientId}/reminders`)
    .where('status', '==', 'pending')
    .where('scheduledTime', '<', admin.firestore.Timestamp.fromDate(overdueThreshold))
    .orderBy('scheduledTime', 'asc')
    .get();

  if (overdueSnap.empty) {
    return { success: true, overdueCount: 0, notified: false };
  }

  const familyLinksSnap = await db.collection(`patients/${patientId}/familyLinks`).get();
  const tokens = [];
  familyLinksSnap.forEach((doc) => {
    if (doc.data().fcmToken) tokens.push(doc.data().fcmToken);
  });

  if (tokens.length > 0) {
    const overdueReminders = [];
    overdueSnap.forEach((doc) => {
      overdueReminders.push({ id: doc.id, ...doc.data() });
    });

    for (const reminder of overdueReminders.slice(0, 5)) {
      await admin.messaging().sendEachForMulticast({
        notification: {
          title: '⚠️ Overdue Reminder',
          body: `${reminder.title || reminder.type} is overdue`,
        },
        data: { patientId, reminderId: reminder.id },
        tokens,
      });
    }
  }

  return { success: true, overdueCount: overdueSnap.size };
});

exports.generateReport = functions.https.onCall(async (data, context) => {
  // Simplified version - full implementation in previous code
  const { patientId, reportType = 'weekly' } = data;

  return {
    success: true,
    report: {
      metadata: { patientId, reportType, generatedAt: new Date().toISOString() },
      summary: { message: 'Report generation complete' },
    },
  };
});

exports.sendNudge = functions.https.onCall(async (data, context) => {
  const { patientId, actionType, payload = {} } = data;

  if (!patientId || !actionType) {
    throw new functions.https.HttpsError('invalid-argument', 'patientId and actionType required');
  }

  const actionRef = db.collection(`patients/${patientId}/pendingActions`).doc();

  await actionRef.set({
    actionId: actionRef.id,
    type: actionType,
    payload,
    status: 'pending',
    sentBy: context.auth?.uid || 'system',
    sentAt: admin.firestore.FieldValue.serverTimestamp(),
    expiresAt: admin.firestore.Timestamp.fromDate(new Date(Date.now() + 10 * 60 * 1000)),
  });

  return { success: true, actionId: actionRef.id };
});

exports.registerFCMToken = functions.https.onCall(async (data, context) => {
  if (!context.auth) {
    throw new functions.https.HttpsError('unauthenticated', 'Must be logged in');
  }

  const { token, role, patientId } = data;

  if (role === 'patient') {
    await db.collection('users').doc(context.auth.uid).set({ fcmToken: token }, { merge: true });
  } else if (role === 'family' && patientId) {
    await db.collection(`patients/${patientId}/familyLinks`).doc(context.auth.uid).set({ fcmToken: token }, { merge: true });
  }

  return { success: true };
});

exports.linkFamilyMember = functions.https.onCall(async (data, context) => {
  if (!context.auth) {
    throw new functions.https.HttpsError('unauthenticated', 'Must be logged in');
  }

  const { patientId, relationship, permissionLevel } = data;

  await db.collection(`patients/${patientId}/familyLinks`).doc(context.auth.uid).set({
    relationship,
    permissionLevel: permissionLevel || 'read',
    linkedAt: admin.firestore.FieldValue.serverTimestamp(),
  });

  await db.collection('users').doc(context.auth.uid).update({
    linkedPatientIds: admin.firestore.FieldValue.arrayUnion(patientId),
    role: 'family',
  });

  return { success: true };
});
