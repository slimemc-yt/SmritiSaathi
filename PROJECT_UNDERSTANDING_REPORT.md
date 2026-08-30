# SmritiSaathi (স্মৃতিসাথী) — Comprehensive Project Understanding Report

**Date:** August 29, 2026  
**Target Platform:** Android (Jetpack Compose, Kotlin, Firebase Spark Tier, Gemini Generative AI)  
**Region & Scope:** Smart India Hackathon (SIH) — Culturally Tailored Dementia Care Companion for North East India (NER)  

---

## 1. Executive Summary

**SmritiSaathi** is a comprehensive, multi-role digital dementia care ecosystem designed specifically for the linguistic, cultural, and healthcare realities of North East India (NER). The system bridges the cognitive care gap across three key user roles:
1. **Patient:** A zero-guilt, cognitive-stage-adaptive interface with zero passwords, gentle reminders, 9 localized brain exercises, reminiscence therapy, and soothing distress de-escalation.
2. **Family / Caregiver:** A centralized command center providing 8-step patient onboarding, real-time behavioral alerts, medication scheduling, cognitive score monitoring, and interactive "Play with Grandpa" remote game invites.
3. **Doctor / Healthcare Worker:** A clinical triage portal with 3-tier severity classification, patient adherence metrics, clinical observation notes, and remote cognitive domain assignments.

The application is architected around a **100% Free-Tier (Firebase Spark + Gemini 1.5 Flash)** paradigm, eliminating cloud function requirements by conducting all business logic, role-based security enforcement, and cognitive scoring on-device with direct Firestore sync.

---

## 2. Architecture Overview

### 2.1 Tech Stack & Dependency Matrix
- **Operating System / Language:** Android 14 (API 34, Min SDK 24) / Kotlin 1.9.0
- **UI Framework:** Jetpack Compose (BOM 2023.10.01, Compiler 1.5.4) with Material 3 Design
- **Dependency Injection:** Dagger Hilt 2.48
- **Authentication:** Firebase Auth 32.5.0, Google Play Services Auth 20.7.0 (OAuth 2.0 Web Client ID)
- **Database & Sync:** Cloud Firestore (Real-time snapshots via Kotlin Coroutines `Flow`)
- **Local Persistence:** Jetpack DataStore Preferences 1.0.0 (UID-scoped session isolation)
- **Generative AI:** Google AI Client SDK (`com.google.ai.client.generativeai:generativeai:0.9.0`, Gemini 1.5 Flash)
- **Speech & Audio:** Android On-Device `TextToSpeech` (via `VoiceAssistantManager`), Jetpack Media3 ExoPlayer 1.2.0
- **Image Loading:** Coil Compose 2.5.0

### 2.2 Directory Structure & Key Modules
```
com.socklet.smritisaathi/
├── MainActivity.kt                      # Single activity entry point, edge-to-edge UI
├── SmritiSaathiApp.kt                  # Application class with @HiltAndroidApp
├── navigation/
│   ├── SmritiSaathiNavigation.kt       # Root NavHost & router (Splash -> Lang -> Role -> Graph)
│   └── Screen.kt                       # Sealed class route definitions
├── data/
│   ├── api/ApiManager.kt               # Gemini 1.5 Flash client & routine generation
│   ├── datastore/DataStoreManager.kt   # UID-scoped session preferences
│   ├── receiver/ReminderBroadcastReceiver.kt # Exact alarm receiver for medication
│   └── seeder/DatabaseSeeder.kt        # Auto-seeder for NER medical colleges & specialists
├── domain/
│   ├── model/                          # Patient, User, Reminder, Alert, GameResult, TierConfig
│   ├── repository/
│   │   ├── AuthRepository.kt           # Google Sign-In, anonymous pairing, role enforcement
│   │   └── PatientRepository.kt        # Central Firestore repository (877 lines)
│   ├── localization/AppStrings.kt      # 9-language translation dictionary
│   ├── scheduler/
│   │   ├── AdaptiveGameScheduler.kt    # Intelligent cognitive domain rotation
│   │   ├── GamePerformanceAnalyzer.kt # Gemini-powered assessment generator
│   │   └── MedicationAlarmScheduler.kt # Exact alarm manager
│   └── games/                          # Core game engine interfaces & session managers
├── ui/
│   ├── onboarding/                     # Splash, Lang, Role, Google Login, Patient Pairing
│   ├── patient/                        # Patient today screen, reminders, distress calming
│   ├── family/                         # Family dashboard, alerts inbox, schedule manager, reports
│   │   └── onboarding/                 # 8-step patient setup wizard
│   ├── doctor/                         # Triage dashboard & patient detail screen
│   ├── games/                          # 9 Jetpack Compose game implementations
│   └── theme/                          # 60:30:10 dementia-safe color palette & typography
```

---

## 3. Core User Flows

### 3.1 Patient Flow
- **Pairing & Bootstrapping:** Patients never type passwords or emails. The device displays a 6-digit numeric pairing code input (`PatientPairingScreen`). Upon entry:
  1. `AuthRepository.signInAnonymously()` creates an anonymous Firebase session.
  2. `PatientRepository.bindPatientDevice(patientId, deviceUid)` associates the device with the patient document.
  3. `DataStoreManager.savePatientPairing()` stores the UID-scoped `paired_patient_id`.
  4. Subsequent launches skip onboarding and boot directly to `PatientHome`.
- **Adaptive Home Experience (`PatientTodayScreen`):**
  - **Tier 1 (Mild):** Full 4-tab bottom navigation (Today, Games, Schedule, Memories).
  - **Tier 2 (Moderate):** Simplified 3-action hub with voice prompts.
  - **Tier 3 (Severe / Enhanced Support):** Single high-contrast primary card, large icons (80dp+), automated calming sounds, and immediate SOS.
- **Distress & Confusion Intervention:** If a patient repeatedly errs in a game or taps *"I feel confused / Calm me"*, the app suppresses error states, plays gentle reassurance via TTS, and routes to `DistressCalmingScreen` while logging a `CONFUSION_DISTRESS_DETECTED` alert for the family.

### 3.2 Family / Caregiver Flow
- **Authentication:** Google Sign-In via `AuthRepository.signInWithGoogle(idToken)`.
- **First-Time Onboarding:** Routes to `FamilyAddPatient` (8-step wizard: Basic Details -> Dementia Stage -> Daily Routine -> Medical Reports -> Family Contacts -> Emergency Contact -> Enhanced Support -> Doctor Selection).
- **Patient Pairing Code:** Generates a 6-digit code (e.g., `582910`) displayed on the dashboard for pairing the patient's device.
- **Caregiver Command Center:**
  - Real-time cognitive performance trends and medication adherence graphs.
  - Behavioral alerts inbox (SOS triggers, medication delays, confusion events).
  - Live medication and routine manager with push notifications.
  - Reminiscence photo album manager with voice captions.
  - **"Play Invite" (Grandchild Mode):** Sends a `PLAY_INVITE` `PendingAction` that rings the patient's tablet like an incoming video call to play a game together.

### 3.3 Doctor / Healthcare Worker Flow
- **Authentication:** Google Sign-In with automated `DOC-XXXX` code generation.
- **Clinical Triage:** Lists assigned patients categorized by clinical risk:
  - **High Risk (Tier 3 / Critical alerts):** Red indicator, immediate review.
  - **Attention Needed (Tier 2 / Adherence < 70%):** Amber indicator.
  - **Stable (Tier 1 / Good adherence):** Green indicator.
- **Clinical Oversight (`DoctorPatientDetailScreen`):**
  - Longitudinal cognitive test history with domain breakdown (Memory, Attention, Executive Function).
  - Medication adherence percentages.
  - Clinical observation note entry synced directly to the family dashboard.
  - Custom priority domain selection for the adaptive game scheduler.

---

## 4. Data Persistence & Sync Strategy

### 4.1 Firestore Entity Hierarchy
```
/users/{uid}                           # Family & Doctor account profiles, roles, linked IDs
/patients/{patientId}                  # Patient master record (Tier, stage, contacts, codes)
    ├── gamePerformances/{docId}      # Detailed 3-round game performance metrics
    ├── gameResults/{docId}           # High-level game score entries
    ├── cognitiveAssessments/{docId}  # Gemini AI cognitive evaluation summaries
    ├── reminders/{reminderId}        # Daily medication and activity schedule
    ├── behavioralAlerts/{alertId}    # SOS and confusion alerts
    ├── pendingActions/{actionId}     # Real-time incoming calls, invites, nudges
    ├── clinicalNotes/{noteId}        # Doctor clinical observations & advice
    └── reminiscence/{itemId}         # Family photo memories with audio captions
/hospitals/{hospitalId}               # Pre-seeded NER medical college directory
/doctors/{doctorId}                   # Pre-seeded NER neurologist / specialist directory
```

### 4.2 Local Persistence (Android DataStore)
- Employs **UID-scoped storage keys** (`user_role_$uid`, `paired_patient_id_$uid`, `preferred_language`, `dementia_tier`) to ensure seamless multi-account switching on shared family devices without cross-session pollution.

### 4.3 Firebase Storage Fallback Architecture
- To maintain 100% zero-cost operation on the Firebase Spark Free Plan (which restricts storage rules and bucket quotas), `PatientRepository` safely catches Firebase Storage exceptions and stores base metadata / local URI fallbacks for medical reports and family photos.

---

## 5. Cognitive Games System

All 9 cognitive games are tailored to South Asian and North East Indian cultural contexts:
1. **Card Matching (`CardMatchingGameScreen`):** 5 difficulty levels (2 to 10 pairs) using familiar regional categories (Assam tea garden items, local fruits, household objects).
2. **Daily Routine Ordering (`DailyRoutineOrderingGameScreen`):** 3-round temporal sequencing (e.g., Morning chai -> Bath -> Lunch -> Medicine).
3. **Face Recognition (`FaceRecognitionGameScreen`):** 3-round photo identification of real family members uploaded by caregivers.
4. **NER Familiar Images (`NERFamiliarImagesGameScreen`):** Identification of North East landmarks and cultural artifacts (Kaziranga rhino, Bihu dhol, Mizo Puan).
5. **Shopping Basket (`ShoppingBasketGameScreen`):** Working memory test remembering grocery lists from local NER bazaars.
6. **Quick Recall (`QuickRecallGameScreen`):** Flash stimulus recall testing short-term visual and auditory memory.
7. **Life Stage Memory (`LifeStageMemoryGameScreen`):** Reminiscence game linking family photos to eras (Childhood, Marriage, Career).
8. **Guess Who's Speaking (`GuessWhosSpeakingGameScreen`):** Auditory voice recognition playing recorded voice notes from family members.
9. **Music Memory (`MusicMemoryGameScreen`):** Audio-based familiar tune and rhythm recognition.

---

## 6. AI and External Integrations

### 6.1 Gemini 1.5 Flash (`ApiManager.kt`)
- **API Key Configuration:** Read securely from `local.properties` -> `BuildConfig.GEMINI_API_KEY`.
- **Dynamic Daily Routine Generation:** `ApiManager.generateRoutine(patientContext, language)` creates customized, dementia-safe daily activity schedules in JSON format.
- **Cognitive Assessment Analysis:** `ApiManager.analyzeCognitivePerformance()` synthesizes recent game accuracy, mistake patterns, and reaction times into multi-domain scores (Memory, Attention, Problem Solving, Reaction Time) and clinical recommendations.

### 6.2 Voice & Speech (`VoiceAssistantManager.kt`)
- Employs Android on-device `TextToSpeech` with Indian locale optimizations (`Locale("hi", "IN")`, `Locale("bn", "IN")`, `Locale("as", "IN")`).
- Speech rate calibrated to **0.85x** for clear comprehension by elderly patients with cognitive decline.

---

## 7. Localization and Accessibility

### 7.1 Linguistic Support
- `AppStrings.kt` contains localization schemas for **9 North East Indian languages**:
  - English (`en`), Hindi (`hi`), Assamese (`as`), Bengali (`bn`), Manipuri (`mni`), Mizo (`lus`), Khasi (`kha`), Garo (`gar`), Bodo (`brx`), and Nagamese (`nmx`).
- Phonetic fallbacks map un-synthesized regional scripts to Bengali or Hindi phonetic TTS engines.

### 7.2 Dementia & Elderly Accessibility Guidelines
- Strict adherence to **Zero-Guilt UX**: No "Game Over", "You Failed", or red error buzzers. Mistakes trigger gentle hints and positive encouragement.
- Touch target sizes minimum 48dp (standard) to 80dp+ (critical actions).
- High contrast color ratios adhering to WCAG 2.1 AA.

---

## 8. Documentation vs Reality (Discrepancy Analysis)

| Area | Documentation Claim | Actual Code Reality | Severity |
| :--- | :--- | :--- | :--- |
| **Bhashini Integration** | Specs mention government Bhashini REST API for translation & TTS | Android codebase exclusively uses Android on-device `TextToSpeech` and local `AppStrings.kt`. No Bhashini network client exists. | **Low (Architectural Decision)** |
| **AI Assessment Trigger** | Documents state Gemini automatically scores games after each round | `GamePerformanceAnalyzer.kt` is implemented with Gemini logic, but `PatientTodayScreen.kt` only calls `saveGameResult` and fails to trigger `GamePerformanceAnalyzer.analyzeAndUpdateAssessment()`. | **High (Feature Disconnect)** |
| **Full App Localization** | Docs claim 9 languages supported across all screens | `AppStrings.kt` is implemented, but only `LanguageSelectionScreen` and parts of onboarding use `rememberAppStrings()`. Most dashboard and game screens contain hardcoded English strings. | **Medium (UX Completeness)** |
| **Doctor Self-Assignment** | Docs claim family can link any verified doctor | Family onboarding has doctor search, but doctors cannot be dynamically linked unless their UID is manually matched in Firestore. Pre-seeded doctor codes are mock stubs. | **Medium (Functional Gap)** |
| **Emergency SOS / Calling** | Specs claim automated phone dialing on distress | Distress screen renders phone intent, but background automated emergency broadcast requires direct telephony permissions not fully granted. | **Low (Security/Platform constraint)** |

---

## 9. Critical Issues and Gaps Identified

1. **Disconnected Cognitive Assessment Pipeline:**
   - `PatientTodayScreen` and `PatientGamesScreen` complete games by saving `GameResult`, but do not pass `GamePerformance` to `GamePerformanceAnalyzer.analyzeAndUpdateAssessment()`. As a result, `/patients/{id}/cognitiveAssessments` is never populated by live gameplay, leaving doctor dashboards dependent on fallback averages.
2. **Incomplete Localization Coverage:**
   - Over 85% of Composable UI screens (e.g., `FamilyDashboardScreen`, `DoctorDashboardScreen`, `CardMatchingGameScreen`) hardcode English text strings rather than reading from `AppStrings.kt` via the patient's preferred language.
3. **Doctor-Patient Relationship Binding:**
   - Doctor dashboard queries `assignedDoctorId == doctorId`. However, during family patient onboarding (`PatientOnboardingScreen`), selecting a doctor from the directory writes the doctor's name but does not reliably populate the doctor's Firebase `uid`, preventing real doctors from seeing newly created patients.
4. **Firebase Storage Error Handling in Onboarding:**
   - When uploading patient profile photos or medical PDFs on the Spark free plan, storage errors are caught, but UI spinners can occasionally hang if the fallback URI is null.

---

## 10. Conclusion & Recommended Next Steps

The project possesses an exceptionally solid architectural foundation, robust authentication, thoughtful dementia-specific UX design, and complete multi-role data structures. The primary areas requiring refinement are:
1. **Connecting the live game completion callback to `GamePerformanceAnalyzer` and Gemini AI assessment pipeline.**
2. **Wiring `AppStrings.kt` across all patient-facing and family-facing Jetpack Compose screens.**
3. **Ensuring seamless Doctor UID assignment during family onboarding.**

*Awaiting user review and instructions before proceeding to execution.*
