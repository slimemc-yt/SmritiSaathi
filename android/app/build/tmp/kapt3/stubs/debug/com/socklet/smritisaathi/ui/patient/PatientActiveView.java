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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\n"}, d2 = {"Lcom/socklet/smritisaathi/ui/patient/PatientActiveView;", "", "(Ljava/lang/String;I)V", "CALM_TODAY", "INCOMING_CALL_OVERLAY", "PLAYING_GAME", "MEDICATION_PROMPT", "MEAL_CHECK_IN", "REMINISCENCE_VIEW", "DISTRESS_CALMING", "app_debug"})
public enum PatientActiveView {
    /*public static final*/ CALM_TODAY /* = new CALM_TODAY() */,
    /*public static final*/ INCOMING_CALL_OVERLAY /* = new INCOMING_CALL_OVERLAY() */,
    /*public static final*/ PLAYING_GAME /* = new PLAYING_GAME() */,
    /*public static final*/ MEDICATION_PROMPT /* = new MEDICATION_PROMPT() */,
    /*public static final*/ MEAL_CHECK_IN /* = new MEAL_CHECK_IN() */,
    /*public static final*/ REMINISCENCE_VIEW /* = new REMINISCENCE_VIEW() */,
    /*public static final*/ DISTRESS_CALMING /* = new DISTRESS_CALMING() */;
    
    PatientActiveView() {
    }
    
    @org.jetbrains.annotations.NotNull
    public static kotlin.enums.EnumEntries<com.socklet.smritisaathi.ui.patient.PatientActiveView> getEntries() {
        return null;
    }
}