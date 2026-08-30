package com.socklet.smritisaathi.ui.patient;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.socklet.smritisaathi.domain.model.*;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler;
import com.socklet.smritisaathi.ui.games.*;
import com.socklet.smritisaathi.ui.theme.Dimensions;
import com.socklet.smritisaathi.util.VoiceAssistantManager;
import java.util.Calendar;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a<\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007\u00a8\u0006\u000e"}, d2 = {"PatientTodayScreen", "", "patient", "Lcom/socklet/smritisaathi/domain/model/Patient;", "repository", "Lcom/socklet/smritisaathi/domain/repository/PatientRepository;", "voiceAssistant", "Lcom/socklet/smritisaathi/util/VoiceAssistantManager;", "gameScheduler", "Lcom/socklet/smritisaathi/domain/scheduler/AdaptiveGameScheduler;", "dataStoreManager", "Lcom/socklet/smritisaathi/data/datastore/DataStoreManager;", "apiManager", "Lcom/socklet/smritisaathi/data/api/ApiManager;", "app_debug"})
public final class PatientTodayScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void PatientTodayScreen(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.Patient patient, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.repository.PatientRepository repository, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.util.VoiceAssistantManager voiceAssistant, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler gameScheduler, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.data.datastore.DataStoreManager dataStoreManager, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.data.api.ApiManager apiManager) {
    }
}