package com.socklet.smritisaathi.ui.patient;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.navigation.NavGraphBuilder;
import androidx.navigation.NavHostController;
import com.socklet.smritisaathi.domain.model.*;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler;
import com.socklet.smritisaathi.navigation.Screen;
import com.socklet.smritisaathi.ui.theme.Dimensions;
import com.socklet.smritisaathi.util.VoiceAssistantManager;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001aD\u0010\u0006\u001a\u00020\u00012\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0007\u001a \u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007\u001a\u001c\u0010\u0015\u001a\u00020\u0001*\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u00a8\u0006\u0019"}, d2 = {"PatientBottomNavigationBar", "", "currentRoute", "", "onNavigate", "Lkotlin/Function1;", "PatientMainScreen", "patientId", "viewModel", "Lcom/socklet/smritisaathi/ui/patient/PatientContainerViewModel;", "repository", "Lcom/socklet/smritisaathi/domain/repository/PatientRepository;", "voiceAssistant", "Lcom/socklet/smritisaathi/util/VoiceAssistantManager;", "gameScheduler", "Lcom/socklet/smritisaathi/domain/scheduler/AdaptiveGameScheduler;", "dataStoreManager", "Lcom/socklet/smritisaathi/data/datastore/DataStoreManager;", "PatientScheduleScreen", "patient", "Lcom/socklet/smritisaathi/domain/model/Patient;", "patientNavGraph", "Landroidx/navigation/NavGraphBuilder;", "navController", "Landroidx/navigation/NavHostController;", "app_debug"})
public final class PatientNavGraphKt {
    
    public static final void patientNavGraph(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavGraphBuilder $this$patientNavGraph, @kotlin.Suppress(names = {"UNUSED_PARAMETER"})
    @org.jetbrains.annotations.NotNull
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull
    java.lang.String patientId) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void PatientMainScreen(@org.jetbrains.annotations.NotNull
    java.lang.String patientId, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.ui.patient.PatientContainerViewModel viewModel, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.repository.PatientRepository repository, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.util.VoiceAssistantManager voiceAssistant, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler gameScheduler, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.data.datastore.DataStoreManager dataStoreManager) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void PatientBottomNavigationBar(@org.jetbrains.annotations.Nullable
    java.lang.String currentRoute, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void PatientScheduleScreen(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.Patient patient, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.repository.PatientRepository repository, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.util.VoiceAssistantManager voiceAssistant) {
    }
}