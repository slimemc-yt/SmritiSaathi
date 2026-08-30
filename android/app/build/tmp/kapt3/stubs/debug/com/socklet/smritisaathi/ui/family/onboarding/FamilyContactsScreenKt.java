package com.socklet.smritisaathi.ui.family.onboarding;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextOverflow;
import com.socklet.smritisaathi.domain.model.FamilyContact;
import com.socklet.smritisaathi.ui.theme.Dimensions;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0083\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2K\u0010\n\u001aG\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\r\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a6\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0003\u001a,\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u00a8\u0006\u001c"}, d2 = {"AddContactDialog", "", "isEditing", "", "initialName", "", "initialRelationship", "initialPhone", "onDismiss", "Lkotlin/Function0;", "onSave", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "relationship", "phone", "FamilyContactCard", "contact", "Lcom/socklet/smritisaathi/domain/model/FamilyContact;", "onEdit", "onRemove", "modifier", "Landroidx/compose/ui/Modifier;", "FamilyContactsScreen", "viewModel", "Lcom/socklet/smritisaathi/ui/family/onboarding/PatientOnboardingViewModel;", "onNext", "onBack", "app_debug"})
public final class FamilyContactsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void FamilyContactsScreen(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.ui.family.onboarding.PatientOnboardingViewModel viewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNext, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void AddContactDialog(boolean isEditing, java.lang.String initialName, java.lang.String initialRelationship, java.lang.String initialPhone, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSave) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void FamilyContactCard(com.socklet.smritisaathi.domain.model.FamilyContact contact, kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove, androidx.compose.ui.Modifier modifier) {
    }
}