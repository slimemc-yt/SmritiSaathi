package com.socklet.smritisaathi.domain.localization;

import androidx.compose.runtime.Composable;
import com.socklet.smritisaathi.data.datastore.DataStoreManager;
import dagger.hilt.android.EntryPointAccessors;
import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000#\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0003\b\u008d\u0001\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u00f5\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0003\u0012\u0006\u0010\u0011\u001a\u00020\u0003\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u0012\u0006\u0010\u0013\u001a\u00020\u0003\u0012\u0006\u0010\u0014\u001a\u00020\u0003\u0012\u0006\u0010\u0015\u001a\u00020\u0003\u0012\u0006\u0010\u0016\u001a\u00020\u0003\u0012\u0006\u0010\u0017\u001a\u00020\u0003\u0012\u0006\u0010\u0018\u001a\u00020\u0003\u0012\u0006\u0010\u0019\u001a\u00020\u0003\u0012\u0006\u0010\u001a\u001a\u00020\u0003\u0012\u0006\u0010\u001b\u001a\u00020\u0003\u0012\u0006\u0010\u001c\u001a\u00020\u0003\u0012\u0006\u0010\u001d\u001a\u00020\u0003\u0012\u0006\u0010\u001e\u001a\u00020\u0003\u0012\u0006\u0010\u001f\u001a\u00020\u0003\u0012\u0006\u0010 \u001a\u00020\u0003\u0012\u0006\u0010!\u001a\u00020\u0003\u0012\u0006\u0010\"\u001a\u00020\u0003\u0012\u0006\u0010#\u001a\u00020\u0003\u0012\u0006\u0010$\u001a\u00020\u0003\u0012\u0006\u0010%\u001a\u00020\u0003\u0012\u0006\u0010&\u001a\u00020\u0003\u0012\u0006\u0010\'\u001a\u00020\u0003\u0012\u0006\u0010(\u001a\u00020\u0003\u0012\u0006\u0010)\u001a\u00020\u0003\u0012\u0006\u0010*\u001a\u00020\u0003\u0012\u0006\u0010+\u001a\u00020\u0003\u0012\u0006\u0010,\u001a\u00020\u0003\u0012\u0006\u0010-\u001a\u00020\u0003\u0012\u0006\u0010.\u001a\u00020\u0003\u0012\u0006\u0010/\u001a\u00020\u0003\u0012\u0006\u00100\u001a\u00020\u0003\u00a2\u0006\u0002\u00101J\t\u0010a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010g\u001a\u00020\u0003H\u00c6\u0003J\t\u0010h\u001a\u00020\u0003H\u00c6\u0003J\t\u0010i\u001a\u00020\u0003H\u00c6\u0003J\t\u0010j\u001a\u00020\u0003H\u00c6\u0003J\t\u0010k\u001a\u00020\u0003H\u00c6\u0003J\t\u0010l\u001a\u00020\u0003H\u00c6\u0003J\t\u0010m\u001a\u00020\u0003H\u00c6\u0003J\t\u0010n\u001a\u00020\u0003H\u00c6\u0003J\t\u0010o\u001a\u00020\u0003H\u00c6\u0003J\t\u0010p\u001a\u00020\u0003H\u00c6\u0003J\t\u0010q\u001a\u00020\u0003H\u00c6\u0003J\t\u0010r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010s\u001a\u00020\u0003H\u00c6\u0003J\t\u0010t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010u\u001a\u00020\u0003H\u00c6\u0003J\t\u0010v\u001a\u00020\u0003H\u00c6\u0003J\t\u0010w\u001a\u00020\u0003H\u00c6\u0003J\t\u0010x\u001a\u00020\u0003H\u00c6\u0003J\t\u0010y\u001a\u00020\u0003H\u00c6\u0003J\t\u0010z\u001a\u00020\u0003H\u00c6\u0003J\t\u0010{\u001a\u00020\u0003H\u00c6\u0003J\t\u0010|\u001a\u00020\u0003H\u00c6\u0003J\t\u0010}\u001a\u00020\u0003H\u00c6\u0003J\t\u0010~\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u007f\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0080\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0081\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0082\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0083\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0084\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0085\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0086\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0087\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0088\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0089\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u008a\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u008b\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u008c\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u008d\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u008e\u0001\u001a\u00020\u0003H\u00c6\u0003J\u00d6\u0003\u0010\u008f\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020\u00032\b\b\u0002\u0010\u0019\u001a\u00020\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u00032\b\b\u0002\u0010\u001b\u001a\u00020\u00032\b\b\u0002\u0010\u001c\u001a\u00020\u00032\b\b\u0002\u0010\u001d\u001a\u00020\u00032\b\b\u0002\u0010\u001e\u001a\u00020\u00032\b\b\u0002\u0010\u001f\u001a\u00020\u00032\b\b\u0002\u0010 \u001a\u00020\u00032\b\b\u0002\u0010!\u001a\u00020\u00032\b\b\u0002\u0010\"\u001a\u00020\u00032\b\b\u0002\u0010#\u001a\u00020\u00032\b\b\u0002\u0010$\u001a\u00020\u00032\b\b\u0002\u0010%\u001a\u00020\u00032\b\b\u0002\u0010&\u001a\u00020\u00032\b\b\u0002\u0010\'\u001a\u00020\u00032\b\b\u0002\u0010(\u001a\u00020\u00032\b\b\u0002\u0010)\u001a\u00020\u00032\b\b\u0002\u0010*\u001a\u00020\u00032\b\b\u0002\u0010+\u001a\u00020\u00032\b\b\u0002\u0010,\u001a\u00020\u00032\b\b\u0002\u0010-\u001a\u00020\u00032\b\b\u0002\u0010.\u001a\u00020\u00032\b\b\u0002\u0010/\u001a\u00020\u00032\b\b\u0002\u00100\u001a\u00020\u0003H\u00c6\u0001J\u0016\u0010\u0090\u0001\u001a\u00030\u0091\u00012\t\u0010\u0092\u0001\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u000b\u0010\u0093\u0001\u001a\u00030\u0094\u0001H\u00d6\u0001J\n\u0010\u0095\u0001\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u00103R\u0011\u0010,\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u00103R\u0011\u0010/\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u00103R\u0011\u0010\u001b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u00103R\u0011\u0010\u001a\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u00103R\u0011\u0010\u0019\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u00103R\u0011\u0010(\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u00103R\u0011\u0010\u001c\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u00103R\u0011\u0010#\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u00103R\u0011\u0010 \u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u00103R\u0011\u0010-\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u00103R\u0011\u0010$\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u00103R\u0011\u0010\u001d\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u00103R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u00103R\u0011\u0010+\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u00103R\u0011\u0010\u0016\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u00103R\u0011\u0010\u001e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u00103R\u0011\u0010*\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u00103R\u0011\u0010\u0015\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u00103R\u0011\u0010\"\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u00103R\u0011\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u00103R\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bI\u00103R\u0011\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bJ\u00103R\u0011\u00100\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bK\u00103R\u0011\u0010%\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bL\u00103R\u0011\u0010\u0018\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bM\u00103R\u0011\u0010.\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bN\u00103R\u0011\u0010)\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bO\u00103R\u0011\u0010\u001f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bP\u00103R\u0011\u0010&\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bQ\u00103R\u0011\u0010\u0017\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bR\u00103R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bS\u00103R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bT\u00103R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bU\u00103R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bV\u00103R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bW\u00103R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bX\u00103R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bY\u00103R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bZ\u00103R\u0011\u0010\'\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b[\u00103R\u0011\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\\\u00103R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b]\u00103R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b^\u00103R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b_\u00103R\u0011\u0010!\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b`\u00103\u00a8\u0006\u0096\u0001"}, d2 = {"Lcom/socklet/smritisaathi/domain/localization/AppStrings;", "", "appName", "", "appTagline", "selectLanguageTitle", "selectLanguageSubtitle", "whoAreYouTitle", "whoAreYouSubtitle", "rolePatientTitle", "rolePatientDesc", "roleFamilyTitle", "roleFamilyDesc", "roleDoctorTitle", "roleDoctorDesc", "demoModeButton", "greetingMorning", "greetingAfternoon", "greetingEvening", "todayActivityPrompt", "start10MinActivity", "feelingConfusedButton", "emergencySosButton", "playInviteReceived", "medicationTitle", "buttonTaken", "buttonRemindLater", "buttonNeedHelp", "cardMatchingTitle", "dailyRoutineTitle", "faceRecognitionTitle", "pairsFound", "checkOrder", "whoIsThis", "greatJob", "caregiverCommandCenter", "cognitiveScore", "medAdherence", "playInvite", "sendNudge", "calmMode", "pairingCodeLabel", "familyInviteCodeLabel", "doctorPortalTitle", "assignedPatients", "clinicalNotes", "normalStatus", "attentionNeeded", "highRisk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAppName", "()Ljava/lang/String;", "getAppTagline", "getAssignedPatients", "getAttentionNeeded", "getButtonNeedHelp", "getButtonRemindLater", "getButtonTaken", "getCalmMode", "getCardMatchingTitle", "getCaregiverCommandCenter", "getCheckOrder", "getClinicalNotes", "getCognitiveScore", "getDailyRoutineTitle", "getDemoModeButton", "getDoctorPortalTitle", "getEmergencySosButton", "getFaceRecognitionTitle", "getFamilyInviteCodeLabel", "getFeelingConfusedButton", "getGreatJob", "getGreetingAfternoon", "getGreetingEvening", "getGreetingMorning", "getHighRisk", "getMedAdherence", "getMedicationTitle", "getNormalStatus", "getPairingCodeLabel", "getPairsFound", "getPlayInvite", "getPlayInviteReceived", "getRoleDoctorDesc", "getRoleDoctorTitle", "getRoleFamilyDesc", "getRoleFamilyTitle", "getRolePatientDesc", "getRolePatientTitle", "getSelectLanguageSubtitle", "getSelectLanguageTitle", "getSendNudge", "getStart10MinActivity", "getTodayActivityPrompt", "getWhoAreYouSubtitle", "getWhoAreYouTitle", "getWhoIsThis", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component40", "component41", "component42", "component43", "component44", "component45", "component46", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class AppStrings {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String appName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String appTagline = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String selectLanguageTitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String selectLanguageSubtitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String whoAreYouTitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String whoAreYouSubtitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String rolePatientTitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String rolePatientDesc = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String roleFamilyTitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String roleFamilyDesc = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String roleDoctorTitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String roleDoctorDesc = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String demoModeButton = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String greetingMorning = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String greetingAfternoon = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String greetingEvening = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String todayActivityPrompt = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String start10MinActivity = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String feelingConfusedButton = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String emergencySosButton = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String playInviteReceived = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String medicationTitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String buttonTaken = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String buttonRemindLater = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String buttonNeedHelp = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String cardMatchingTitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String dailyRoutineTitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String faceRecognitionTitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String pairsFound = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String checkOrder = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String whoIsThis = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String greatJob = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String caregiverCommandCenter = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String cognitiveScore = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String medAdherence = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String playInvite = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String sendNudge = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String calmMode = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String pairingCodeLabel = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String familyInviteCodeLabel = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String doctorPortalTitle = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String assignedPatients = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String clinicalNotes = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String normalStatus = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String attentionNeeded = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String highRisk = null;
    
    public AppStrings(@org.jetbrains.annotations.NotNull
    java.lang.String appName, @org.jetbrains.annotations.NotNull
    java.lang.String appTagline, @org.jetbrains.annotations.NotNull
    java.lang.String selectLanguageTitle, @org.jetbrains.annotations.NotNull
    java.lang.String selectLanguageSubtitle, @org.jetbrains.annotations.NotNull
    java.lang.String whoAreYouTitle, @org.jetbrains.annotations.NotNull
    java.lang.String whoAreYouSubtitle, @org.jetbrains.annotations.NotNull
    java.lang.String rolePatientTitle, @org.jetbrains.annotations.NotNull
    java.lang.String rolePatientDesc, @org.jetbrains.annotations.NotNull
    java.lang.String roleFamilyTitle, @org.jetbrains.annotations.NotNull
    java.lang.String roleFamilyDesc, @org.jetbrains.annotations.NotNull
    java.lang.String roleDoctorTitle, @org.jetbrains.annotations.NotNull
    java.lang.String roleDoctorDesc, @org.jetbrains.annotations.NotNull
    java.lang.String demoModeButton, @org.jetbrains.annotations.NotNull
    java.lang.String greetingMorning, @org.jetbrains.annotations.NotNull
    java.lang.String greetingAfternoon, @org.jetbrains.annotations.NotNull
    java.lang.String greetingEvening, @org.jetbrains.annotations.NotNull
    java.lang.String todayActivityPrompt, @org.jetbrains.annotations.NotNull
    java.lang.String start10MinActivity, @org.jetbrains.annotations.NotNull
    java.lang.String feelingConfusedButton, @org.jetbrains.annotations.NotNull
    java.lang.String emergencySosButton, @org.jetbrains.annotations.NotNull
    java.lang.String playInviteReceived, @org.jetbrains.annotations.NotNull
    java.lang.String medicationTitle, @org.jetbrains.annotations.NotNull
    java.lang.String buttonTaken, @org.jetbrains.annotations.NotNull
    java.lang.String buttonRemindLater, @org.jetbrains.annotations.NotNull
    java.lang.String buttonNeedHelp, @org.jetbrains.annotations.NotNull
    java.lang.String cardMatchingTitle, @org.jetbrains.annotations.NotNull
    java.lang.String dailyRoutineTitle, @org.jetbrains.annotations.NotNull
    java.lang.String faceRecognitionTitle, @org.jetbrains.annotations.NotNull
    java.lang.String pairsFound, @org.jetbrains.annotations.NotNull
    java.lang.String checkOrder, @org.jetbrains.annotations.NotNull
    java.lang.String whoIsThis, @org.jetbrains.annotations.NotNull
    java.lang.String greatJob, @org.jetbrains.annotations.NotNull
    java.lang.String caregiverCommandCenter, @org.jetbrains.annotations.NotNull
    java.lang.String cognitiveScore, @org.jetbrains.annotations.NotNull
    java.lang.String medAdherence, @org.jetbrains.annotations.NotNull
    java.lang.String playInvite, @org.jetbrains.annotations.NotNull
    java.lang.String sendNudge, @org.jetbrains.annotations.NotNull
    java.lang.String calmMode, @org.jetbrains.annotations.NotNull
    java.lang.String pairingCodeLabel, @org.jetbrains.annotations.NotNull
    java.lang.String familyInviteCodeLabel, @org.jetbrains.annotations.NotNull
    java.lang.String doctorPortalTitle, @org.jetbrains.annotations.NotNull
    java.lang.String assignedPatients, @org.jetbrains.annotations.NotNull
    java.lang.String clinicalNotes, @org.jetbrains.annotations.NotNull
    java.lang.String normalStatus, @org.jetbrains.annotations.NotNull
    java.lang.String attentionNeeded, @org.jetbrains.annotations.NotNull
    java.lang.String highRisk) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAppName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAppTagline() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSelectLanguageTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSelectLanguageSubtitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWhoAreYouTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWhoAreYouSubtitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRolePatientTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRolePatientDesc() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRoleFamilyTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRoleFamilyDesc() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRoleDoctorTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getRoleDoctorDesc() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDemoModeButton() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGreetingMorning() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGreetingAfternoon() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGreetingEvening() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTodayActivityPrompt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getStart10MinActivity() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFeelingConfusedButton() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getEmergencySosButton() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPlayInviteReceived() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getMedicationTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getButtonTaken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getButtonRemindLater() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getButtonNeedHelp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCardMatchingTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDailyRoutineTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFaceRecognitionTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPairsFound() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCheckOrder() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWhoIsThis() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getGreatJob() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCaregiverCommandCenter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCognitiveScore() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getMedAdherence() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPlayInvite() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSendNudge() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCalmMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPairingCodeLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFamilyInviteCodeLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDoctorPortalTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAssignedPatients() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getClinicalNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getNormalStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getAttentionNeeded() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getHighRisk() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component20() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component21() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component22() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component23() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component24() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component25() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component26() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component27() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component28() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component29() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component30() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component31() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component32() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component33() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component34() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component35() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component36() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component37() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component38() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component39() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component40() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component41() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component42() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component43() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component44() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component45() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component46() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.localization.AppStrings copy(@org.jetbrains.annotations.NotNull
    java.lang.String appName, @org.jetbrains.annotations.NotNull
    java.lang.String appTagline, @org.jetbrains.annotations.NotNull
    java.lang.String selectLanguageTitle, @org.jetbrains.annotations.NotNull
    java.lang.String selectLanguageSubtitle, @org.jetbrains.annotations.NotNull
    java.lang.String whoAreYouTitle, @org.jetbrains.annotations.NotNull
    java.lang.String whoAreYouSubtitle, @org.jetbrains.annotations.NotNull
    java.lang.String rolePatientTitle, @org.jetbrains.annotations.NotNull
    java.lang.String rolePatientDesc, @org.jetbrains.annotations.NotNull
    java.lang.String roleFamilyTitle, @org.jetbrains.annotations.NotNull
    java.lang.String roleFamilyDesc, @org.jetbrains.annotations.NotNull
    java.lang.String roleDoctorTitle, @org.jetbrains.annotations.NotNull
    java.lang.String roleDoctorDesc, @org.jetbrains.annotations.NotNull
    java.lang.String demoModeButton, @org.jetbrains.annotations.NotNull
    java.lang.String greetingMorning, @org.jetbrains.annotations.NotNull
    java.lang.String greetingAfternoon, @org.jetbrains.annotations.NotNull
    java.lang.String greetingEvening, @org.jetbrains.annotations.NotNull
    java.lang.String todayActivityPrompt, @org.jetbrains.annotations.NotNull
    java.lang.String start10MinActivity, @org.jetbrains.annotations.NotNull
    java.lang.String feelingConfusedButton, @org.jetbrains.annotations.NotNull
    java.lang.String emergencySosButton, @org.jetbrains.annotations.NotNull
    java.lang.String playInviteReceived, @org.jetbrains.annotations.NotNull
    java.lang.String medicationTitle, @org.jetbrains.annotations.NotNull
    java.lang.String buttonTaken, @org.jetbrains.annotations.NotNull
    java.lang.String buttonRemindLater, @org.jetbrains.annotations.NotNull
    java.lang.String buttonNeedHelp, @org.jetbrains.annotations.NotNull
    java.lang.String cardMatchingTitle, @org.jetbrains.annotations.NotNull
    java.lang.String dailyRoutineTitle, @org.jetbrains.annotations.NotNull
    java.lang.String faceRecognitionTitle, @org.jetbrains.annotations.NotNull
    java.lang.String pairsFound, @org.jetbrains.annotations.NotNull
    java.lang.String checkOrder, @org.jetbrains.annotations.NotNull
    java.lang.String whoIsThis, @org.jetbrains.annotations.NotNull
    java.lang.String greatJob, @org.jetbrains.annotations.NotNull
    java.lang.String caregiverCommandCenter, @org.jetbrains.annotations.NotNull
    java.lang.String cognitiveScore, @org.jetbrains.annotations.NotNull
    java.lang.String medAdherence, @org.jetbrains.annotations.NotNull
    java.lang.String playInvite, @org.jetbrains.annotations.NotNull
    java.lang.String sendNudge, @org.jetbrains.annotations.NotNull
    java.lang.String calmMode, @org.jetbrains.annotations.NotNull
    java.lang.String pairingCodeLabel, @org.jetbrains.annotations.NotNull
    java.lang.String familyInviteCodeLabel, @org.jetbrains.annotations.NotNull
    java.lang.String doctorPortalTitle, @org.jetbrains.annotations.NotNull
    java.lang.String assignedPatients, @org.jetbrains.annotations.NotNull
    java.lang.String clinicalNotes, @org.jetbrains.annotations.NotNull
    java.lang.String normalStatus, @org.jetbrains.annotations.NotNull
    java.lang.String attentionNeeded, @org.jetbrains.annotations.NotNull
    java.lang.String highRisk) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}