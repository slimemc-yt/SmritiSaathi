package com.socklet.smritisaathi.data.api

import android.content.Context
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Centralized API manager for external services (Gemini, TTS, etc.)
 * Handles API calls with proper error handling and fallbacks.
 */
@Singleton
class ApiManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = getApiKey()
    )

    /**
     * Get API key from local.properties or BuildConfig
     * NEVER hardcode API keys in source code
     */
    private fun getApiKey(): String {
        return try {
            // Try to get from BuildConfig (set via local.properties)
            val field = Class.forName("com.socklet.smritisaathi.BuildConfig")
                .getField("GEMINI_API_KEY")
            field.get(null) as String
        } catch (e: Exception) {
            Log.w("ApiManager", "GEMINI_API_KEY not found in BuildConfig, using fallback")
            // Fallback for development - should be set in local.properties
            ""
        }
    }

    /**
     * Generate content using Gemini API
     * @param prompt The prompt to send to Gemini
     * @param language Language code for response (e.g., "en", "hi", "as")
     * @return Generated text or fallback message
     */
    suspend fun generateContent(
        prompt: String,
        language: String = "en"
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            if (getApiKey().isBlank()) {
                return@withContext Result.failure(Exception("Gemini API key not configured"))
            }

            val languageInstruction = when (language) {
                "hi" -> "Respond in Hindi."
                "as" -> "Respond in Assamese."
                "bn" -> "Respond in Bengali."
                "mni" -> "Respond in Manipuri."
                "lus" -> "Respond in Mizo."
                "kha" -> "Respond in Khasi."
                "gar" -> "Respond in Garo."
                "brx" -> "Respond in Bodo."
                "nmx" -> "Respond in Nagamese."
                else -> "Respond in English."
            }

            val fullPrompt = "$prompt\n\n$languageInstruction"
            val response = generativeModel.generateContent(fullPrompt)
            
            Log.d("ApiManager", "✓ Gemini API call successful")
            Result.success(response.text ?: "")
        } catch (e: Exception) {
            Log.e("ApiManager", "✗ Gemini API call failed: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Generate structured routine using Gemini
     * @param patientContext Patient profile information
     * @param language Language code
     * @return Generated routine as JSON string
     */
    suspend fun generateRoutine(
        patientContext: String,
        language: String = "en"
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val prompt = """
                Generate a daily routine for a dementia patient with the following profile:
                $patientContext
                
                Return the routine as a JSON array with this structure:
                [
                  {
                    "time": "HH:mm",
                    "activity": "Activity name",
                    "type": "medication|meal|game|rest|exercise",
                    "description": "Brief description"
                  }
                ]
                
                Keep activities simple, clear, and appropriate for dementia care.
                Include 3 meals, medications, cognitive games, and rest periods.
            """.trimIndent()

            generateContent(prompt, language)
        } catch (e: Exception) {
            Log.e("ApiManager", "Routine generation failed: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Analyze game performance and generate cognitive assessment
     * @param performances List of recent game performances
     * @param patientContext Patient profile information
     * @param language Language code for response
     * @return Cognitive assessment as JSON string
     */
    suspend fun analyzeCognitivePerformance(
        performances: List<com.socklet.smritisaathi.domain.model.GamePerformance>,
        patientContext: String,
        language: String = "en"
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val performanceSummary = performances.joinToString("\n") { perf ->
                """
                Game: ${perf.gameType}
                Score: ${perf.score}/${perf.maxScore}
                Accuracy: ${(perf.accuracy * 100).toInt()}%
                Time: ${perf.timeSpentSeconds}s
                Difficulty: ${perf.difficultyLevel}
                Mistakes: ${perf.mistakes}
                """.trimIndent()
            }

            val prompt = """
                Analyze the cognitive performance of a dementia patient and generate a comprehensive assessment.
                
                Patient Context:
                $patientContext
                
                Recent Game Performances:
                $performanceSummary
                
                Generate a cognitive assessment as JSON with this structure:
                {
                  "overallScore": 0-100,
                  "memoryScore": 0-100,
                  "attentionScore": 0-100,
                  "problemSolvingScore": 0-100,
                  "reactionTimeScore": 0-100,
                  "trend": "improving|stable|declining",
                  "recommendations": ["recommendation1", "recommendation2", "recommendation3"]
                }
                
                Consider:
                - Memory games affect memoryScore
                - Routine ordering affects problemSolvingScore
                - Reaction time in games affects reactionTimeScore
                - Accuracy and mistakes affect attentionScore
                - Overall performance trend
                - Age-appropriate expectations
                
                Provide actionable recommendations for cognitive improvement.
            """.trimIndent()

            generateContent(prompt, language)
        } catch (e: Exception) {
            Log.e("ApiManager", "Cognitive analysis failed: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Google Cloud TTS API call
     * Free tier: 1 million characters/month
     * @param text Text to synthesize
     * @param languageCode Language code (e.g., "en-IN", "hi-IN", "bn-IN")
     * @return Audio content as ByteArray or null if failed
     */
    suspend fun synthesizeSpeech(
        text: String,
        languageCode: String = "en-IN"
    ): Result<ByteArray> = withContext(Dispatchers.IO) {
        try {
            // Map our language codes to Google Cloud TTS codes
            val ttsLanguageCode = when (languageCode) {
                "en" -> "en-IN"
                "hi" -> "hi-IN"
                "as" -> "bn-IN" // Assamese not directly supported, use Bengali as closest
                "bn" -> "bn-IN"
                "mni" -> "bn-IN" // Manipuri not supported, fallback to Bengali
                "lus" -> "bn-IN" // Mizo not supported, fallback
                "kha" -> "bn-IN" // Khasi not supported, fallback
                "gar" -> "bn-IN" // Garo not supported, fallback
                "brx" -> "hi-IN" // Bodo not supported, fallback to Hindi
                "nmx" -> "en-IN" // Nagamese, use English
                else -> "en-IN"
            }

            // Note: This is a simplified implementation
            // For production, use Google Cloud TTS client library
            // This would require adding the dependency and proper authentication
            
            Log.w("ApiManager", "Google Cloud TTS not fully implemented yet, using Android TTS fallback")
            Result.failure(Exception("Use Android TTS for now"))
        } catch (e: Exception) {
            Log.e("ApiManager", "TTS synthesis failed: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Check if Gemini API is properly configured
     */
    fun isGeminiConfigured(): Boolean {
        return getApiKey().isNotBlank()
    }
}
