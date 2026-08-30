package com.socklet.smritisaathi.domain.scheduler

import android.util.Log
import com.socklet.smritisaathi.data.api.ApiManager
import com.socklet.smritisaathi.domain.model.CognitiveAssessment
import com.socklet.smritisaathi.domain.model.GamePerformance
import com.socklet.smritisaathi.domain.model.Patient
import com.socklet.smritisaathi.domain.repository.PatientRepository
import kotlinx.coroutines.flow.first
import org.json.JSONObject
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamePerformanceAnalyzer @Inject constructor(
    private val repository: PatientRepository,
    private val apiManager: ApiManager
) {
    /**
     * Analyze game performance and update cognitive assessment
     * Called after each game completion
     */
    suspend fun analyzeAndUpdateAssessment(
        patientId: String,
        performance: GamePerformance,
        patient: Patient
    ) {
        try {
            Log.d("GamePerformanceAnalyzer", "Analyzing game performance for patient: $patientId")
            
            // Save the game performance
            repository.saveGamePerformance(patientId, performance)
            
            // Get recent performances (last 20 games)
            val recentPerformances = repository.getGamePerformancesFlow(patientId, 20).first()
            
            if (recentPerformances.size >= 3) { // Need at least 3 games for meaningful analysis
                // Create patient context for Gemini
                val patientContext = """
                    Patient Name: ${patient.name}
                    Age: ${patient.age}
                    Dementia Stage: ${patient.dementiaStage.name}
                    Preferred Language: ${patient.preferredLanguage}
                    Total Games Played: ${recentPerformances.size}
                """.trimIndent()
                
                // Call Gemini API for cognitive assessment
                val result = apiManager.analyzeCognitivePerformance(
                    performances = recentPerformances,
                    patientContext = patientContext,
                    language = patient.preferredLanguage
                )
                
                if (result.isSuccess) {
                    val assessmentJson = result.getOrNull()
                    if (assessmentJson != null) {
                        val assessment = parseCognitiveAssessment(assessmentJson, patientId)
                        repository.saveCognitiveAssessment(patientId, assessment)
                        Log.d("GamePerformanceAnalyzer", "✓ Cognitive assessment saved: overall=${assessment.overallScore}")
                    }
                } else {
                    Log.w("GamePerformanceAnalyzer", "Gemini analysis failed: ${result.exceptionOrNull()?.message}")
                }
            } else {
                Log.d("GamePerformanceAnalyzer", "Not enough games for analysis (${recentPerformances.size}/3)")
            }
        } catch (e: Exception) {
            Log.e("GamePerformanceAnalyzer", "Analysis failed: ${e.message}", e)
        }
    }
    
    private fun parseCognitiveAssessment(json: String, patientId: String): CognitiveAssessment {
        return try {
            // Extract JSON from response (Gemini might wrap it in markdown)
            val jsonStart = json.indexOf("{")
            val jsonEnd = json.lastIndexOf("}") + 1
            val cleanJson = if (jsonStart >= 0 && jsonEnd > jsonStart) {
                json.substring(jsonStart, jsonEnd)
            } else {
                json
            }
            
            val jsonObj = JSONObject(cleanJson)
            
            CognitiveAssessment(
                patientId = patientId,
                overallScore = jsonObj.optInt("overallScore", 50),
                memoryScore = jsonObj.optInt("memoryScore", 50),
                attentionScore = jsonObj.optInt("attentionScore", 50),
                problemSolvingScore = jsonObj.optInt("problemSolvingScore", 50),
                reactionTimeScore = jsonObj.optInt("reactionTimeScore", 50),
                trend = jsonObj.optString("trend", "stable"),
                recommendations = mutableListOf<String>().apply {
                    val recs = jsonObj.optJSONArray("recommendations")
                    if (recs != null) {
                        for (i in 0 until recs.length()) {
                            add(recs.getString(i))
                        }
                    }
                },
                assessedAt = Date(),
                period = "daily"
            )
        } catch (e: Exception) {
            Log.e("GamePerformanceAnalyzer", "Failed to parse assessment JSON: ${e.message}")
            // Return default assessment
            CognitiveAssessment(
                patientId = patientId,
                overallScore = 50,
                memoryScore = 50,
                attentionScore = 50,
                problemSolvingScore = 50,
                reactionTimeScore = 50,
                trend = "stable",
                recommendations = listOf("Continue playing games regularly"),
                assessedAt = Date(),
                period = "daily"
            )
        }
    }
}
