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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\r\u000e\u000f\u0010B\u001f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b\u0082\u0001\u0004\u0011\u0012\u0013\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/socklet/smritisaathi/ui/patient/PatientScreen;", "", "route", "", "title", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;)V", "getIcon", "()Landroidx/compose/ui/graphics/vector/ImageVector;", "getRoute", "()Ljava/lang/String;", "getTitle", "Games", "Home", "Reminders", "Reminiscence", "Lcom/socklet/smritisaathi/ui/patient/PatientScreen$Games;", "Lcom/socklet/smritisaathi/ui/patient/PatientScreen$Home;", "Lcom/socklet/smritisaathi/ui/patient/PatientScreen$Reminders;", "Lcom/socklet/smritisaathi/ui/patient/PatientScreen$Reminiscence;", "app_debug"})
public abstract class PatientScreen {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String route = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.compose.ui.graphics.vector.ImageVector icon = null;
    
    private PatientScreen(java.lang.String route, java.lang.String title, androidx.compose.ui.graphics.vector.ImageVector icon) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRoute() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.compose.ui.graphics.vector.ImageVector getIcon() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/patient/PatientScreen$Games;", "Lcom/socklet/smritisaathi/ui/patient/PatientScreen;", "()V", "app_debug"})
    public static final class Games extends com.socklet.smritisaathi.ui.patient.PatientScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.patient.PatientScreen.Games INSTANCE = null;
        
        private Games() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/patient/PatientScreen$Home;", "Lcom/socklet/smritisaathi/ui/patient/PatientScreen;", "()V", "app_debug"})
    public static final class Home extends com.socklet.smritisaathi.ui.patient.PatientScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.patient.PatientScreen.Home INSTANCE = null;
        
        private Home() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/patient/PatientScreen$Reminders;", "Lcom/socklet/smritisaathi/ui/patient/PatientScreen;", "()V", "app_debug"})
    public static final class Reminders extends com.socklet.smritisaathi.ui.patient.PatientScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.patient.PatientScreen.Reminders INSTANCE = null;
        
        private Reminders() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/socklet/smritisaathi/ui/patient/PatientScreen$Reminiscence;", "Lcom/socklet/smritisaathi/ui/patient/PatientScreen;", "()V", "app_debug"})
    public static final class Reminiscence extends com.socklet.smritisaathi.ui.patient.PatientScreen {
        @org.jetbrains.annotations.NotNull
        public static final com.socklet.smritisaathi.ui.patient.PatientScreen.Reminiscence INSTANCE = null;
        
        private Reminiscence() {
        }
    }
}