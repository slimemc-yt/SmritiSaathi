package com.socklet.smritisaathi.ui.family.onboarding;

import androidx.compose.animation.AnimatedContentTransitionScope;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextAlign;
import androidx.navigation.NavHostController;
import com.socklet.smritisaathi.domain.model.DementiaStage;
import com.socklet.smritisaathi.ui.theme.Dimensions;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\"\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001a=\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2!\u0010\u000f\u001a\u001d\u0012\u0013\u0012\u00110\u000e\u00a2\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0007\u001a\"\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u000e2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u00a8\u0006\u0019"}, d2 = {"OnboardingCompletionScreen", "", "uiState", "Lcom/socklet/smritisaathi/ui/family/onboarding/PatientOnboardingUiState;", "onSave", "Lkotlin/Function0;", "OnboardingProgressIndicator", "currentStep", "", "totalSteps", "modifier", "Landroidx/compose/ui/Modifier;", "PatientOnboardingScreen", "familyMemberId", "", "onOnboardingComplete", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "patientId", "viewModel", "Lcom/socklet/smritisaathi/ui/family/onboarding/PatientOnboardingViewModel;", "SummaryRow", "label", "value", "app_debug"})
public final class PatientOnboardingScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void PatientOnboardingScreen(@org.jetbrains.annotations.NotNull
    java.lang.String familyMemberId, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOnboardingComplete, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.ui.family.onboarding.PatientOnboardingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void OnboardingProgressIndicator(int currentStep, int totalSteps, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void OnboardingCompletionScreen(com.socklet.smritisaathi.ui.family.onboarding.PatientOnboardingUiState uiState, kotlin.jvm.functions.Function0<kotlin.Unit> onSave) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void SummaryRow(java.lang.String label, java.lang.String value, androidx.compose.ui.Modifier modifier) {
    }
}