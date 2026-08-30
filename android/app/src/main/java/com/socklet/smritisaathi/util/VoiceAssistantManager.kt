package com.socklet.smritisaathi.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.socklet.smritisaathi.data.datastore.DataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceAssistantManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataStoreManager: DataStoreManager
) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var currentLanguageCode: String = "en" // Default to English

    init {
        tts = TextToSpeech(context, this)
        
        // Observe language changes from DataStore and update TTS automatically
        CoroutineScope(Dispatchers.Main).launch {
            dataStoreManager.preferredLanguageFlow.collect { languageCode ->
                Log.d("VoiceAssistant", "Language preference: $languageCode")
                setLanguageByCode(languageCode)
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            Log.d("VoiceAssistant", "TTS initialized successfully")
        } else {
            Log.e("VoiceAssistant", "TextToSpeech initialization failed with status $status")
        }
    }

    fun setLanguageByCode(code: String) {
        currentLanguageCode = code
        if (!isInitialized || tts == null) return

        val locale = when (code.lowercase()) {
            "hi" -> Locale("hi", "IN")
            "en" -> Locale("en", "IN")
            "as" -> Locale("as", "IN") // Assamese
            "bn" -> Locale("bn", "IN") // Bengali (close phonetic base for some regional scripts)
            "mni" -> Locale("mni", "IN") // Manipuri
            "lus" -> Locale("lus", "IN") // Mizo
            else -> Locale("en", "IN")
        }

        val result = tts?.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            // Fallback to Indian English or Hindi
            tts?.setLanguage(Locale("hi", "IN"))
        }
        tts?.setSpeechRate(0.85f) // Slightly slower for clear elderly comprehension
        tts?.setPitch(1.0f)
    }

    fun speak(text: String, onDone: (() -> Unit)? = null) {
        if (text.isBlank()) return
        if (!isInitialized || tts == null) {
            Log.w("VoiceAssistant", "TTS not ready yet, text: $text")
            return
        }

        if (onDone != null) {
            val utteranceId = "utterance_${System.currentTimeMillis()}"
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {
                    onDone()
                }
                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    onDone()
                }
            })
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        } else {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    fun stop() {
        tts?.stop()
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
    }
}
