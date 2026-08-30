package com.socklet.smritisaathi.ui.patient

import androidx.lifecycle.ViewModel
import com.socklet.smritisaathi.data.api.ApiManager
import com.socklet.smritisaathi.data.datastore.DataStoreManager
import com.socklet.smritisaathi.domain.repository.PatientRepository
import com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler
import com.socklet.smritisaathi.util.VoiceAssistantManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientContainerViewModel @Inject constructor(
    val repository: PatientRepository,
    val voiceAssistant: VoiceAssistantManager,
    val gameScheduler: AdaptiveGameScheduler,
    val dataStoreManager: DataStoreManager,
    val apiManager: ApiManager
) : ViewModel()
