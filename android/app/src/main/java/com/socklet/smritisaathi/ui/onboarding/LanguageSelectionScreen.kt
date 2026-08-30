package com.socklet.smritisaathi.ui.onboarding

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.socklet.smritisaathi.data.datastore.DataStoreManager
import com.socklet.smritisaathi.domain.localization.getAppStringsForLanguage
import com.socklet.smritisaathi.domain.model.SUPPORTED_LANGUAGES
import com.socklet.smritisaathi.ui.components.LanguageButton
import com.socklet.smritisaathi.ui.components.PrimaryButton
import com.socklet.smritisaathi.ui.theme.Dimensions
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun LanguageSelectionScreen(
    onLanguageSelected: () -> Unit,
    @Suppress("UNUSED_PARAMETER") viewModel: AuthViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val dataStoreManager = hiltViewModel<com.socklet.smritisaathi.ui.patient.PatientContainerViewModel>().dataStoreManager
    // val coroutineScope = rememberCoroutineScope()
    var selectedLanguage by remember { mutableStateOf("en") } // Default English
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    val strings = remember(selectedLanguage) { getAppStringsForLanguage(selectedLanguage) }

    LaunchedEffect(Unit) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("hi", "IN")
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            tts?.stop()
            tts?.shutdown()
            tts = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(Dimensions.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = strings.selectLanguageTitle,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimensions.Space8))

            Text(
                text = strings.selectLanguageSubtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space12)
        ) {
            SUPPORTED_LANGUAGES.forEach { language ->
                LanguageButton(
                    language = language,
                    isSelected = selectedLanguage == language.code,
                    onClick = { selectedLanguage = language.code },
                    onSpeakerClick = {
                        val sampleGreeting = when (language.code) {
                            "as" -> "নমস্কাৰ, স্মৃতিসাথীলৈ স্বাগতম"
                            "hi" -> "नमस्ते, स्मृतिसाथी में आपका स्वागत है"
                            "mni" -> "নমস্কার, স্মৃতিসাথীদা তরাম্না ওকচরি"
                            else -> "Hello, welcome to SmritiSaathi"
                        }
                        tts?.speak(sampleGreeting, TextToSpeech.QUEUE_FLUSH, null, "sample_${language.code}")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.Space16))

        PrimaryButton(
            text = "Continue ➔",
            onClick = {
                // Save language preference to DataStore
                kotlinx.coroutines.MainScope().launch {
                    dataStoreManager.savePreferredLanguage(selectedLanguage)
                    android.util.Log.d("LanguageSelection", "✓ Saved language preference: $selectedLanguage")
                }
                onLanguageSelected()
            },
            enabled = selectedLanguage.isNotBlank()
        )
    }
}
