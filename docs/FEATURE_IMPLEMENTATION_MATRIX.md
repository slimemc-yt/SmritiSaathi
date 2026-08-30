# SmritiSaathi - Master Feature Implementation Matrix

This document maps all product specifications defined in `SIH_Dementia_App_Full_Spec.md` to exact source code implementations across Android, iOS, Wear OS, and Backend Cloud Functions.

---

## 1. Authentication, Language & Role System

| Feature Requirement | Android Implementation | iOS Implementation | Backend / Firestore |
| :--- | :--- | :--- | :--- |
| **Regional Language Selection (9 NER Languages)** | `ui/auth/LanguageSelectionScreen.kt` | `ios/SmritiSaathi/Views/AuthViews.swift` | Stored in `/patients/{id}.preferredLanguage` |
| **Role Selector (Patient, Family, Doctor)** | `ui/auth/RoleSelectionScreen.kt` | `ios/SmritiSaathi/Views/AuthViews.swift` | Stored in `/users/{userId}.role` |
| **Phone OTP Authentication** | `ui/auth/PhoneAuthScreen.kt` & `AuthViewModel.kt` | `ios/SmritiSaathi/Views/AuthViews.swift` | Firebase Phone Auth Provider |
| **Zero-Password Permanent Patient Sign-In** | `ui/patient/PatientTodayScreen.kt` | `ios/SmritiSaathi/Views/PatientViews.swift` | Persistent session token |

---

## 2. Patient "Today" Single-Task Engine & UX

| Feature Requirement | Android Implementation | iOS Implementation |
| :--- | :--- | :--- |
| **Time-of-Day Greeting & Single Big Activity Button** | `ui/patient/PatientTodayScreen.kt` | `ios/SmritiSaathi/Views/PatientViews.swift` |
| **Incoming-Call Styled Task Overlay** | `ui/patient/IncomingCallReminderOverlay.kt` | `ios/SmritiSaathi/Views/PatientViews.swift` |
| **Persistent SOS Emergency Button** | `ui/patient/PatientTodayScreen.kt` | `ios/SmritiSaathi/Views/PatientViews.swift` |
| **Distress Detection & Calming Screen (3 Mistakes)** | `ui/patient/DistressCalmingScreen.kt` | `ios/SmritiSaathi/Views/PatientViews.swift` |
| **Adaptive Game Recommendation AI Selector** | `domain/scheduler/AdaptiveGameScheduler.kt` | `ios/SmritiSaathi/Services/Services.swift` |
| **Tier 1/2/3 Configuration Engine** | `domain/model/TierConfig.kt` | `ios/SmritiSaathi/Models/Models.swift` |

---

## 3. Full Cognitive Games Suite (All 9 Games)

| Game Type | Android Compose File | iOS SwiftUI File |
| :--- | :--- | :--- |
| **1. Face Recognition Game** | `ui/games/FaceRecognitionGameScreen.kt` | `ios/SmritiSaathi/Views/GamesViews.swift` |
| **2. Card Matching Game** | `ui/games/CardMatchingGameScreen.kt` | `ios/SmritiSaathi/Views/GamesViews.swift` |
| **3. Daily Routine Ordering Game** | `ui/games/DailyRoutineOrderingGameScreen.kt` | `ios/SmritiSaathi/Views/GamesViews.swift` |
| **4. NER Familiar Images Game** | `ui/games/NERFamiliarImagesGameScreen.kt` | `ios/SmritiSaathi/Views/GamesViews.swift` |
| **5. Shopping Basket Challenge** | `ui/games/ShoppingBasketGameScreen.kt` | `ios/SmritiSaathi/Views/GamesViews.swift` |
| **6. Quick Recall Story Game** | `ui/games/QuickRecallGameScreen.kt` | `ios/SmritiSaathi/Views/GamesViews.swift` |
| **7. Life Stage Memory Album** | `ui/games/LifeStageMemoryGameScreen.kt` | `ios/SmritiSaathi/Views/GamesViews.swift` |
| **8. Guess Who's Speaking (Voice Note)** | `ui/games/GuessWhosSpeakingGameScreen.kt` | `ios/SmritiSaathi/Views/GamesViews.swift` |
| **9. Music & Folk Song Resonance** | `ui/games/MusicMemoryGameScreen.kt` | `ios/SmritiSaathi/Views/GamesViews.swift` |

---

## 4. Medication & Routine Assistant

| Feature Requirement | Android Implementation | iOS Implementation | Backend / Function |
| :--- | :--- | :--- | :--- |
| **Incoming-Call Medication Prompt (Taken / 15m / SOS)** | `ui/patient/MedicationPromptScreen.kt` | `ios/SmritiSaathi/Views/PatientViews.swift` | `/patients/{id}/reminders/{reminderId}` |
| **Daily Routine Setup & Nap Intervals** | `ui/family/FamilyRemindersManagerScreen.kt` | `ios/SmritiSaathi/Views/FamilyViews.swift` | `/patients/{id}.routine` |
| **Overdue Reminder Caregiver Alert** | Firestore Snapshot Listener | Real-Time Push Listener | `backend/smritisaathi/index.js: onReminderOverdue` |

---

## 5. Family Member Command Center

| Feature Requirement | Android Implementation | iOS Implementation | Backend / Function |
| :--- | :--- | :--- | :--- |
| **Caregiver Dashboard (Trends & Adherence)** | `ui/family/FamilyDashboardScreen.kt` | `ios/SmritiSaathi/Views/FamilyViews.swift` | Real-time flow aggregation |
| **"Play With Grandpa" Real-Time Game Invite** | `ui/family/PlayWithGrandpaScreen.kt` | `ios/SmritiSaathi/Views/FamilyViews.swift` | `/patients/{id}/pendingActions` listener |
| **Remote Voice Nudge & Calming Dispatcher** | `ui/family/FamilyDashboardScreen.kt` | `ios/SmritiSaathi/Views/FamilyViews.swift` | `backend/smritisaathi/index.js: sendNudge` |
| **Behavioral Change Alerts Inbox** | `ui/family/BehavioralAlertsInboxScreen.kt` | `ios/SmritiSaathi/Views/FamilyViews.swift` | `/patients/{id}/behavioralAlerts` |
| **Clinical Progress Report Exporter** | `ui/family/FamilyReportScreen.kt` | `ios/SmritiSaathi/Views/FamilyViews.swift` | `backend/smritisaathi/index.js: generateReport` |
| **Reminiscence Photo & Audio Uploader** | `ui/family/FamilyReminiscenceManagerScreen.kt` | `ios/SmritiSaathi/Views/FamilyViews.swift` | Firebase Storage & `/reminiscenceContent` |

---

## 6. Doctor & Healthcare Worker Portal

| Feature Requirement | Android Implementation | iOS Implementation |
| :--- | :--- | :--- |
| **Multi-Patient District Triage List** | `ui/doctor/DoctorDashboardScreen.kt` | `ios/SmritiSaathi/Views/DoctorViews.swift` |
| **Longitudinal Trend & Adherence Inspection** | `ui/doctor/DoctorPatientDetailScreen.kt` | `ios/SmritiSaathi/Views/DoctorViews.swift` |
| **Clinical Observations & Family Guidance Push** | `ui/doctor/DoctorPatientDetailScreen.kt` | `ios/SmritiSaathi/Views/DoctorViews.swift` |

---

## 7. Wear OS Smartwatch Companion

| Feature Requirement | Wear OS Implementation |
| :--- | :--- |
| **Upcoming Routine & Medicine Card** | `wearos/src/main/java/com/socklet/smritisaathi/wearos/presentation/MainActivity.kt` |
| **Wrist Action Triggers (Taken ✅ / 15m Snooze ⏰)** | `wearos/src/main/java/com/socklet/smritisaathi/wearos/presentation/MainActivity.kt` |
| **Always-Accessible SOS Alert** | `wearos/src/main/java/com/socklet/smritisaathi/wearos/presentation/MainActivity.kt` |

---

## 8. Backend Cloud Functions (Free Tier Spark Plan)

| Cloud Function Name | Trigger Type | Implementation Path |
| :--- | :--- | :--- |
| `onGameResultWritten` | Firestore Document Write (`/patients/{id}/gameResults/{id}`) | `backend/smritisaathi/index.js:32` |
| `sendNudge` | Callable HTTPS (`onCall`) | `backend/smritisaathi/index.js:81` |
| `generateReport` | Callable HTTPS (`onCall`) | `backend/smritisaathi/index.js:110` |
| `geminiQuizProxy` | Callable HTTPS (`onCall`) | `backend/smritisaathi/index.js:174` |
| `bhashiniVoiceProxy` | Callable HTTPS (`onCall`) | `backend/smritisaathi/index.js:236` |
