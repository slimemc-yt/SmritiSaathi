package com.socklet.smritisaathi.ui.doctor;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.socklet.smritisaathi.domain.model.DementiaStage;
import com.socklet.smritisaathi.domain.model.DoctorPatientRequest;
import com.socklet.smritisaathi.domain.model.Patient;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import com.socklet.smritisaathi.ui.theme.Dimensions;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aV\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u00a8\u0006\f"}, d2 = {"DoctorDashboardScreen", "", "doctorId", "", "doctorCode", "doctorName", "repository", "Lcom/socklet/smritisaathi/domain/repository/PatientRepository;", "onSelectPatient", "Lkotlin/Function1;", "onSignOut", "Lkotlin/Function0;", "app_debug"})
public final class DoctorDashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    @kotlin.Suppress(names = {"UNUSED_PARAMETER"})
    public static final void DoctorDashboardScreen(@org.jetbrains.annotations.NotNull
    java.lang.String doctorId, @org.jetbrains.annotations.NotNull
    java.lang.String doctorCode, @org.jetbrains.annotations.NotNull
    java.lang.String doctorName, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.repository.PatientRepository repository, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSelectPatient, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onSignOut) {
    }
}