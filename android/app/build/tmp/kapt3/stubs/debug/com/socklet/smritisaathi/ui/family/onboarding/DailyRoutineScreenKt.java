package com.socklet.smritisaathi.ui.family.onboarding;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.style.TextOverflow;
import com.socklet.smritisaathi.domain.model.MealTime;
import com.socklet.smritisaathi.domain.model.MedicineTime;
import com.socklet.smritisaathi.domain.model.NapTime;
import com.socklet.smritisaathi.ui.theme.Dimensions;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000^\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a(\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u001a*\u0010\r\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a(\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u001a*\u0010\u0014\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a(\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u001a*\u0010\u0018\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00010\u0010H\u0003\u001a0\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u001aV\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000526\u0010\u001f\u001a2\u0012\u0013\u0012\u00110!\u00a2\u0006\f\b\"\u0012\b\b#\u0012\u0004\b\b($\u0012\u0013\u0012\u00110!\u00a2\u0006\f\b\"\u0012\b\b#\u0012\u0004\b\b(%\u0012\u0004\u0012\u00020\u00010 H\u0003\u001a8\u0010&\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\'\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020)2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0003\u001a\u0010\u0010+\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020\u001bH\u0002\u00a8\u0006-"}, d2 = {"DailyRoutineScreen", "", "viewModel", "Lcom/socklet/smritisaathi/ui/family/onboarding/PatientOnboardingViewModel;", "onNext", "Lkotlin/Function0;", "onBack", "MealTimeCard", "meal", "Lcom/socklet/smritisaathi/domain/model/MealTime;", "onRemove", "modifier", "Landroidx/compose/ui/Modifier;", "MealTimeDialog", "onDismiss", "onAdd", "Lkotlin/Function1;", "MedicineTimeCard", "medicine", "Lcom/socklet/smritisaathi/domain/model/MedicineTime;", "MedicineTimeDialog", "NapTimeCard", "nap", "Lcom/socklet/smritisaathi/domain/model/NapTime;", "NapTimeDialog", "SectionHeader", "title", "", "subtitle", "onAddClick", "TimePickerDialog", "onTimeSelected", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "hour", "minute", "TimeSettingCard", "time", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "onClick", "formatTime12Hour", "time24", "app_debug"})
public final class DailyRoutineScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void DailyRoutineScreen(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.ui.family.onboarding.PatientOnboardingViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNext, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void SectionHeader(java.lang.String title, java.lang.String subtitle, kotlin.jvm.functions.Function0<kotlin.Unit> onAddClick, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void TimeSettingCard(java.lang.String title, java.lang.String time, androidx.compose.ui.graphics.vector.ImageVector icon, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void NapTimeCard(com.socklet.smritisaathi.domain.model.NapTime nap, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void MealTimeCard(com.socklet.smritisaathi.domain.model.MealTime meal, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void MedicineTimeCard(com.socklet.smritisaathi.domain.model.MedicineTime medicine, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void TimePickerDialog(java.lang.String title, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function2<? super java.lang.Integer, ? super java.lang.Integer, kotlin.Unit> onTimeSelected) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void NapTimeDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.NapTime, kotlin.Unit> onAdd) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void MealTimeDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.MealTime, kotlin.Unit> onAdd) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void MedicineTimeDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.MedicineTime, kotlin.Unit> onAdd) {
    }
    
    private static final java.lang.String formatTime12Hour(java.lang.String time24) {
        return null;
    }
}