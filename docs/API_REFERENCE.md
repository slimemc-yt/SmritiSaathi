# External API Reference

This document covers the external APIs used in SmritiSaathi and their free-tier limitations.

---

## Google Gemini API

### Overview
Gemini is used for AI-powered features:
- Quiz question generation for cognitive games
- Conversational chatbot responses
- Reminiscence prompt generation from photos

### Free Tier Limits (Gemini 1.5 Flash)

| Metric | Limit | Notes |
|--------|-------|-------|
| Requests per minute (RPM) | 15 | Shared across all users |
| Requests per day (RPD) | 1,500 | Resets at midnight UTC |
| Tokens per minute (TPM) | 1,000,000 | Input + output tokens |
| Tokens per day (TPD) | 32,000 | Free tier limit |
| Max input tokens | 1,048,576 | ~1M tokens |
| Max output tokens | 8,192 | Per request |

### Rate Limiting Strategy

The app implements multiple layers of rate limiting:

1. **Server-side (Cloud Functions)**
   - Tracks per-user requests in Firestore
   - Enforces 15 RPM / 1,500 RPD limits
   - Returns `retryAfter` when limits exceeded

2. **Caching Layer**
   | Operation | Cache Duration | Rationale |
   |-----------|---------------|-----------|
   | Quiz questions | 1 hour | Same topic/difficulty combo reused |
   | Chat responses | None | Dynamic, personalized |
   | Reminiscence prompts | 24 hours | Photo-based prompts reusable |

3. **Client-side Fallbacks**
   - Pre-generated quiz question banks
   - Default chat responses
   - Cached audio for common phrases

### Setting Up Gemini API

1. Go to [Google AI Studio](https://aistudio.google.com/app/apikey)
2. Create an API key
3. Add to Firebase environment:
   ```bash
   firebase functions:config:set gemini.api_key="YOUR_API_KEY"
   ```

4. Update `index.js` to read from config:
   ```javascript
   const apiKey = functions.config().gemini?.api_key || process.env.GEMINI_API_KEY;
   ```

### Cost Optimization Tips

1. **Reduce token usage:**
   - Use concise prompts
   - Limit conversation history to last 5 messages
   - Request specific output formats (JSON)

2. **Maximize caching:**
   - Cache quiz questions by topic/difficulty
   - Pre-generate common reminiscence prompts
   - Store audio for frequently used phrases

3. **Graceful degradation:**
   - Always provide fallback content
   - Use offline quiz banks when API unavailable
   - Display cached content when rate limited

---

## Bhashini API (Government of India)

### Overview
Bhashini provides speech-to-text and text-to-speech for Indian languages, including NER regional languages.

### Supported Languages

| Code | Language | Region | TTS | STT |
|------|----------|--------|-----|-----|
| en | English | Pan-India | ✅ | ✅ |
| hi | Hindi | Pan-India | ✅ | ✅ |
| as | Assamese | Assam | ✅ | ✅ |
| brx | Bodo | Assam | ✅ | ✅ |
| kha | Khasi | Meghalaya | ✅ | ✅ |
| gar | Garo | Meghalaya | ✅ | ✅ |
| lus | Mizo | Mizoram | ✅ | ✅ |
| mni | Manipuri | Manipur | ✅ | ✅ |
| nmx | Nagamese | Nagaland | ✅ | ✅ |

### Free Tier Limits

Bhashini is a government initiative providing free access for public use:

| Metric | Limit | Notes |
|--------|-------|-------|
| Requests per day | ~10,000 | Per application |
| Max audio duration | 60 seconds | Per STT request |
| Max text length | 500 chars | Per TTS request |
| Supported formats | WAV, MP3, OGG | For STT input |
| Output format | WAV | For TTS output |

### API Architecture

Bhashini uses ULCA (Unified Language Conversation Architecture):

1. **Service Discovery** (`/model/getModelsPipeline`)
   - Get available TTS/ASR service IDs for language
   - Must call before inference

2. **Inference Pipeline** (`/services/inference/pipeline`)
   - Actual TTS/STT processing
   - Requires service ID from discovery step

### Setting Up Bhashini API

1. Register at [Bhashini ULCA Portal](https://bhashini.gov.in/ulca)
2. Create an application to get credentials
3. Add to Firebase environment:
   ```bash
   firebase functions:config:set bhashini.user_id="YOUR_USER_ID" bhashini.api_key="YOUR_API_KEY"
   ```

### Fallback Strategy

When Bhashini API fails, the response includes `useOnDevice: true`:

```javascript
// Response format when fallback needed
{
  "success": false,
  "useOnDevice": true,
  "reason": "api_error" | "unsupported_language" | "network_error",
  "message": "Error details",
  "fallbackLanguage": "en"  // Suggested fallback language
}
```

**Client-side fallback implementation:**

**Android (Kotlin):**
```kotlin
// Use Android TTS when Bhashini unavailable
val tts = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
    if (status == TextToSpeech.SUCCESS) {
        tts.language = Locale(languageCode)
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_utterance_id")
    }
})

// Use Android SpeechRecognizer for STT
val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
}
recognizer.startListening(intent)
```

**iOS (Swift):**
```swift
// Use AVSpeechSynthesizer for TTS
let synthesizer = AVSpeechSynthesizer()
let utterance = AVSpeechUtterance(string: text)
utterance.voice = AVSpeechSynthesisVoice(language: languageCode)
synthesizer.speak(utterance)

// Use SFSpeechRecognizer for STT
let recognizer = SFSpeechRecognizer(locale: Locale(identifier: languageCode))
let request = SFSpeechAudioBufferRecognitionRequest()
// Configure and start recognition
```

### Caching Strategy

| Operation | Cache Duration | Key Components |
|-----------|---------------|----------------|
| TTS Audio | 7 days | Text hash + language + gender |
| STT | None | Real-time, no caching |

TTS caching is aggressive because:
- Same phrases frequently used (reminders, prompts)
- Audio generation is computationally expensive
- Cached audio reduces API calls significantly

---

## Rate Limiting Implementation

### Firestore-based Rate Limiting

Rate limits are tracked in `/rateLimits/{userId}_{apiType}`:

```javascript
{
  requests: [timestamp1, timestamp2, ...],  // Array of request times
  lastRequest: timestamp
}
```

**Checking rate limits:**
```javascript
// Cloud Function automatically checks before API calls
const rateCheck = await checkRateLimit(userId, 'gemini', 'chat');
if (!rateCheck.allowed) {
  // Returns HTTP 429 with retryAfter
}
```

### Client-Side Best Practices

1. **Implement exponential backoff:**
   ```javascript
   async function callWithBackoff(fn, maxRetries = 3) {
     for (let i = 0; i < maxRetries; i++) {
       try {
         return await fn();
       } catch (error) {
         if (error.code === 'resource-exhausted') {
           const delay = Math.pow(2, i) * 1000;
           await sleep(delay);
         } else {
           throw error;
         }
       }
     }
   }
   ```

2. **Pre-cache common content:**
   - Quiz questions on app install
   - Common TTS phrases (numbers, days, months)
   - Reminiscence prompts for uploaded photos

3. **Batch requests:**
   - Generate multiple quiz questions in one call
   - Queue TTS requests and process in batches

---

## Environment Variables

Required environment variables for Cloud Functions:

```bash
# Gemini API
GEMINI_API_KEY=your_gemini_api_key

# Bhashini API
BHASHINI_USER_ID=your_bhashini_user_id
BHASHINI_API_KEY=your_bhashini_api_key
```

Set via Firebase CLI:
```bash
firebase functions:config:set gemini.api_key="YOUR_KEY"
firebase functions:config:set bhashini.user_id="YOUR_ID" bhashini.api_key="YOUR_KEY"
```

Access in code:
```javascript
const geminiApiKey = functions.config().gemini?.api_key || process.env.GEMINI_API_KEY;
const bhashiniUserId = functions.config().bhashini?.user_id || process.env.BHASHINI_USER_ID;
```

---

## Error Handling

### Error Response Format

All API functions return consistent error responses:

```javascript
{
  "success": false,
  "error": "Error message",
  "code": "error_code",
  "useOnDevice": true,  // For TTS/STT fallback
  "isFallback": true,   // When fallback content provided
  "retryAfter": 60      // Seconds until retry (for rate limits)
}
```

### Error Codes

| Code | HTTP Status | Meaning | Client Action |
|------|-------------|---------|---------------|
| `invalid-argument` | 400 | Missing required parameter | Fix request |
| `unauthenticated` | 401 | User not logged in | Show login |
| `permission-denied` | 403 | Not authorized | Check permissions |
| `resource-exhausted` | 429 | Rate limit exceeded | Wait `retryAfter` seconds |
| `internal` | 500 | Server error | Use fallback content |

---

## Monitoring & Alerts

### Tracking API Usage

Monitor usage in Firebase Console:
- Functions → Logs → Search for "Rate limit"
- Firestore → Usage → Check `rateLimits` collection size

### Setting Up Alerts

Configure budget alerts in Google Cloud Console:
1. Billing → Budgets & alerts
2. Create budget for Firebase project
3. Set alert at 50%, 75%, 90% of free tier limits

### Log Analysis

Key log patterns to monitor:
- `"Rate limit exceeded"` - Users hitting limits
- `"TTS failed"` - Bhashini availability issues
- `"Quiz generation failed"` - Gemini API issues
- `"Cache hit"` - Caching effectiveness

---

## Summary: Free Tier Compliance

| API | Cost | Rate Limit Strategy | Fallback |
|-----|------|---------------------|----------|
| Gemini | Free (15 RPM, 1500 RPD) | Firestore-based tracking | Pre-generated content |
| Bhashini | Free (~10K RPD) | Request queuing | On-device TTS/STT |
| Firebase Functions | Free (125K invocations/month) | N/A | Client-side processing |

**Total estimated monthly cost: $0** (within free tier limits with proper implementation)
