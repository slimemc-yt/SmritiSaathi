package com.socklet.smritisaathi.ui.family.onboarding;

import android.net.Uri;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import com.socklet.smritisaathi.domain.model.MedicalReport;
import com.socklet.smritisaathi.ui.theme.Dimensions;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a*\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a(\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u001a,\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a0\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u00a8\u0006\u0015"}, d2 = {"AddReportDialog", "", "onDismiss", "Lkotlin/Function0;", "onReportTypeSelected", "Lkotlin/Function1;", "", "MedicalReportCard", "report", "Lcom/socklet/smritisaathi/domain/model/MedicalReport;", "onRemove", "modifier", "Landroidx/compose/ui/Modifier;", "MedicalReportsScreen", "viewModel", "Lcom/socklet/smritisaathi/ui/family/onboarding/PatientOnboardingViewModel;", "onNext", "onBack", "PendingMedicalReportCard", "fileName", "reportType", "app_debug"})
public final class MedicalReportsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void MedicalReportsScreen(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.ui.family.onboarding.PatientOnboardingViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNext, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void PendingMedicalReportCard(java.lang.String fileName, java.lang.String reportType, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void AddReportDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onReportTypeSelected) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void MedicalReportCard(com.socklet.smritisaathi.domain.model.MedicalReport report, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove, androidx.compose.ui.Modifier modifier) {
    }
}