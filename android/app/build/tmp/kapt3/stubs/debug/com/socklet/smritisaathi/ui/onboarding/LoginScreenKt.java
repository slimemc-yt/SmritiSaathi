package com.socklet.smritisaathi.ui.onboarding;

import android.app.Activity;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextAlign;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.socklet.smritisaathi.domain.model.UserRole;
import com.socklet.smritisaathi.ui.theme.Dimensions;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aL\u0010\u0000\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\f"}, d2 = {"LoginScreen", "", "initialRole", "Lcom/socklet/smritisaathi/domain/model/UserRole;", "onLoginSuccess", "Lkotlin/Function2;", "", "onNavigateToOtp", "Lkotlin/Function1;", "", "viewModel", "Lcom/socklet/smritisaathi/ui/onboarding/AuthViewModel;", "app_debug"})
public final class LoginScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void LoginScreen(@org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.UserRole initialRole, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super com.socklet.smritisaathi.domain.model.UserRole, ? super java.lang.Boolean, kotlin.Unit> onLoginSuccess, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToOtp, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.ui.onboarding.AuthViewModel viewModel) {
    }
}