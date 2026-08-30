# SmritiSaathi - Authentication, Role Separation, Pairing & Multi-Account Synchronization

## 1. Existing Registration and Onboarding Audit

| Existing Component / Flow | Location / File | Current Status | Connected to App? | Reusable? | Required Action |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **8-Step Patient Onboarding Flow** | `ui/family/onboarding/PatientOnboardingScreen.kt` | ✅ Fully Implemented (8 Steps) | 🟡 Disconnected from post-login flow | 🟢 100% Reusable | **CONNECT & REUSE**: Trigger immediately when a new Family user registers or taps "Add Patient". |
| **Patient Basic Details Form** | `ui/family/onboarding/PatientBasicDetailsScreen.kt` | ✅ Fully Implemented | 🟡 Inside onboarding sub-graph | 🟢 Reusable | **KEEP & CONNECT**: Captures name, age, gender, photo. |
| **Dementia Stage / Tier Selector** | `ui/family/onboarding/DementiaStageScreen.kt` | ✅ Fully Implemented | 🟡 Inside onboarding sub-graph | 🟢 Reusable | **KEEP**: Sets Tier 1 (Mild), Tier 2 (Moderate), Tier 3 (Severe). |
| **Medical Reports Uploader** | `ui/family/onboarding/MedicalReportsScreen.kt` | ✅ Fully Implemented | 🟡 Inside onboarding sub-graph | 🟢 Reusable | **KEEP**: Local photo/doc picker for prescriptions. |
| **Family Contacts Manager** | `ui/family/onboarding/FamilyContactsScreen.kt` | ✅ Fully Implemented | 🟡 Inside onboarding sub-graph | 🟢 Reusable | **KEEP**: Feeds Face Recognition game. |
| **Doctor / Hospital Assignment** | `ui/family/onboarding/DoctorHospitalScreen.kt` | ✅ Fully Implemented | 🟡 Inside onboarding sub-graph | 🟢 Reusable | **KEEP & EXTEND**: Feeds Doctor-Patient linking. |
| **Daily Routine Setup** | `ui/family/onboarding/DailyRoutineScreen.kt` | ✅ Fully Implemented | 🟡 Inside onboarding sub-graph | 🟢 Reusable | **KEEP**: Wake/sleep/meals/medicines setup. |
| **Emergency Contact & SOS** | `ui/family/onboarding/EmergencyContactScreen.kt` | ✅ Fully Implemented | 🟡 Inside onboarding sub-graph | 🟢 Reusable | **KEEP**: Configures 1-tap SOS number. |
| **Enhanced Support / Kiosk Consent** | `ui/family/onboarding/EnhancedSupportScreen.kt` | ✅ Fully Implemented | 🟡 Inside onboarding sub-graph | 🟢 Reusable | **KEEP**: Tier 3 screen pinning consent. |
| **Role Selection** | `ui/onboarding/RoleSelectionScreen.kt` | ✅ Implemented | ✅ Connected | 🟢 Reusable | **EXTEND**: Add explicit Demo Mode button. |
| **Language Selection** | `ui/onboarding/LanguageSelectionScreen.kt` | 🟡 Incomplete (Did not save) | ✅ Connected | 🟢 Reusable | **FIX**: Save to DataStore & apply `AppStrings`. |
| **Google Sign-In & Login** | `ui/onboarding/LoginScreen.kt` | 🔴 Broken (Placeholder Client ID) | ✅ Connected | 🟢 Reusable | **FIX**: Handle real client ID + demo fallback. |
| **Doctor Clinical Portal** | `ui/doctor/DoctorDashboardScreen.kt` | ✅ Implemented | ✅ Connected | 🟢 Reusable | **EXTEND**: Differentiate from Family dashboard. |
| **Doctor Patient Detail & Notes** | `ui/doctor/DoctorPatientDetailScreen.kt` | ✅ Implemented | ✅ Connected | 🟢 Reusable | **KEEP**: Clinical notes & family guidance push. |
| **Caregiver Command Center** | `ui/family/FamilyDashboardScreen.kt` | ✅ Implemented | ✅ Connected | 🟢 Reusable | **KEEP & POLISH**: Device pairing banner & live sync. |
| **Patient Device Pairing** | `ui/onboarding/PatientPairingScreen.kt` | ✅ Implemented | ✅ Connected | 🟢 Reusable | **KEEP**: 6-Digit code pairing with auto-boot. |

---

## 2. Real Account Mode vs. Demo Mode

### Real Account Mode
- Real Google Sign-In authenticates with Firebase Auth.
- Real `/users/{uid}` created with role `FAMILY` or `DOCTOR`.
- New Family users start with an **empty state** prompting them to register their first patient via the 8-step `PatientOnboardingScreen`.
- Real patients receive a unique `pairingCode` (e.g., `842913`) and `familyInviteCode` (e.g., `FAM-4912`).
- Patient device enters the pairing code once and synchronizes in real time.
- Assigned doctors only see patients explicitly assigned to them.

### Demo Mode (For Hackathon Judges / Fast Testing)
- Explicit **"⚡ Launch Demo Mode"** button on Role Selection and Login screens.
- Instantly loads pre-populated demo patient (*Hemlata Devi*, 72, Assamese, Mild Dementia) with complete game history, live reminders, sample alerts, and clinical notes.
- Completely isolated from real user Firestore documents.

---

## 3. Strict Role Separation

```text
┌─────────────────────────────────────────────────────────────┐
│                    AUTHENTICATED IDENTITY                   │
└──────────────────────────────┬──────────────────────────────┘
                               │
                ┌──────────────┼──────────────┐
                ▼              ▼              ▼
         ┌────────────┐ ┌────────────┐ ┌─────────────┐
         │   FAMILY   │ │   DOCTOR   │ │   PATIENT   │
         │   MEMBER   │ │   PORTAL   │ │   DEVICE    │
         └─────┬──────┘ └─────┬──────┘ └──────┬──────┘
               │              │               │
  • Full Caregiver Control    │               │
  • Register & Edit Patient   │               │
  • Configure Routine/Meds    │               │
  • Send Nudges & Invites     │               │
  • View Family Alerts        │               │
                              │               │
         • Multi-Patient Triage List          │
         • Read Cognitive Trends              │
         • Clinical Notes & Family Guidance   │
         • CANNOT modify game history         │
                                              │
                       • Single-Task "Today" View
                       • Zero-Password Auto-Boot
                       • Play Cognitive Games
                       • Respond to Medicine Prompts
                       • 1-Tap SOS Emergency Call
```

---

## 4. Family Dashboard vs. Doctor Dashboard Differences

| Dimension | Family Caregiver Dashboard | Doctor Clinical Portal |
| :--- | :--- | :--- |
| **Core Purpose** | Daily caregiving, routine management, family emotional bonding | Clinical oversight, longitudinal trends, risk triage |
| **Patient Scope** | Family patient(s) owned or shared via family invite code | Multiple district patients assigned via doctor referral code |
| **Interactions** | Send Nudges, "Play with Grandpa" invites, Trigger Calming Mode | Push Clinical Guidance to Family, Record Clinical Observations |
| **Data Modifications**| Edit wake/sleep/nap times, medicine schedules, family photos | Add/edit clinical observation notes only (Read-only for games) |
| **Alerts Focus** | Medication help requests, missed check-ins, confusion alerts | Triage risk sorting (High Risk / Attention Needed / Stable) |
| **Device Pairing** | Displays 6-digit Device Pairing Code for the patient phone | No device pairing features |

---

## 5. End-to-End Google Sign-In Fix

### The Break in the Chain
- `LoginScreen.kt` had a hardcoded placeholder OAuth client ID (`1070465757550-placeholder.apps.googleusercontent.com`).
- When Google Play Services ran `signInIntent`, it threw `ApiException 10 (DEVELOPER_ERROR)` or returned a null `idToken`.

### The Fix
1. Dynamically read `R.string.default_web_client_id` (auto-generated by Google Services Gradle plugin).
2. If `idToken` is valid, call `authRepository.signInWithGoogle(idToken, role)`.
3. If running on an emulator or debug build without Web Client ID, seamlessly fall back to Google profile identity (`account.email`, `account.displayName`) or instant Demo mode so development is never blocked.
4. After sign-in:
   - Check if `/users/{uid}` exists.
   - If not, route Family to `PatientOnboardingScreen` and Doctor to Doctor setup.
   - If exists, navigate to `FamilyHome` or `DoctorHome`.

---

## 6. Proper Multilingual Localization Architecture

### Centralized Dictionary (`domain/localization/AppStrings.kt`)
Supports English (`en`), Assamese (`as`), Hindi (`hi`), Bodo (`brx`), Manipuri (`mni`), Khasi (`kha`), Garo (`gar`), Mizo (`lus`), and Nagamese (`nmx`).

```kotlin
// Reactive strings composable
val strings = rememberAppStrings()
Text(text = strings.todayGreetingMorning) // "Good morning" / "নমস্কাৰ, শুভ প্ৰভাত" / "सुप्रभात"
```
- Saved persistently in `DataStoreManager.preferredLanguageFlow`.
- Automatically changes visible UI text across all screens upon language selection.
