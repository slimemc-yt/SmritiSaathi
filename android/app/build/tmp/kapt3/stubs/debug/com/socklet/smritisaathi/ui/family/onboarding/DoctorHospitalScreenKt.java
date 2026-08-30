package com.socklet.smritisaathi.ui.family.onboarding;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextOverflow;
import com.socklet.smritisaathi.domain.model.Doctor;
import com.socklet.smritisaathi.domain.model.Hospital;
import com.socklet.smritisaathi.domain.model.User;
import com.socklet.smritisaathi.ui.theme.Dimensions;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u001a8\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a*\u0010\n\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a&\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a,\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001a&\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a\u001e\u0010\u0017\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\t2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a\u001e\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00042\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u00a8\u0006\u001a"}, d2 = {"AddDoctorDialog", "", "hospitals", "", "Lcom/socklet/smritisaathi/domain/model/Hospital;", "onDismiss", "Lkotlin/Function0;", "onAdd", "Lkotlin/Function1;", "Lcom/socklet/smritisaathi/domain/model/Doctor;", "AddHospitalDialog", "DoctorCard", "doctor", "selected", "", "onClick", "DoctorHospitalScreen", "viewModel", "Lcom/socklet/smritisaathi/ui/family/onboarding/PatientOnboardingViewModel;", "onNext", "onBack", "HospitalCard", "hospital", "SelectedDoctorCard", "onRemove", "SelectedHospitalCard", "app_debug"})
public final class DoctorHospitalScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void DoctorHospitalScreen(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.ui.family.onboarding.PatientOnboardingViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNext, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void SelectedDoctorCard(com.socklet.smritisaathi.domain.model.Doctor doctor, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void SelectedHospitalCard(com.socklet.smritisaathi.domain.model.Hospital hospital, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void DoctorCard(com.socklet.smritisaathi.domain.model.Doctor doctor, boolean selected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void HospitalCard(com.socklet.smritisaathi.domain.model.Hospital hospital, boolean selected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void AddHospitalDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.Hospital, kotlin.Unit> onAdd) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void AddDoctorDialog(java.util.List<com.socklet.smritisaathi.domain.model.Hospital> hospitals, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.Doctor, kotlin.Unit> onAdd) {
    }
}