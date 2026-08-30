# SmritiSaathi - Accessibility & Elderly Usability QA Checklist

## 1. Elderly Accessibility Standards (WCAG 2.1 AA Compliant)

| Dimension | Standard / Guideline | SmritiSaathi Implementation Status |
| :--- | :--- | :--- |
| **Touch Targets** | Min 48dp (Tier 1), 64dp (Tier 2), 80dp (Tier 3) | **PASS**: All action buttons in `PatientTodayScreen`, `IncomingCallReminderOverlay`, `MedicationPromptScreen`, and all 9 cognitive games exceed standard touch dimensions. |
| **Typography & Scaling** | Minimum 18sp base body, 24sp+ headings | **PASS**: Dynamic scaling with large default typography optimized for elderly eyesight. |
| **Color Contrast** | Minimum 4.5:1 for normal text, 3:1 for large text | **PASS**: High contrast themes with dark text on warm, soft backgrounds (`#FFFBF0`, `#1E3A8A`, `#047857`). |
| **Voice-First Interaction** | Multi-lingual TTS for every prompt & question | **PASS**: `VoiceAssistantManager` with automatic Bhashini API proxy + native Android TTS fallback across 9 regional languages. |
| **Cognitive Load** | Single-task-at-a-time display | **PASS**: Zero multi-tier menus for patients. Incoming-call styled full screen takeover alerts. |

---

## 2. Global "No-Guilt" UX Verification

- [x] **Zero Error / Failure Screens**: When an incorrect answer is selected in cognitive games, no red cross or buzzer sounds are played. The app provides encouraging, gentle feedback (e.g., *"Let's take a look at another clue!"* or softly advances).
- [x] **No Streak Pressure / Penalties**: No countdown timers, streak counters, or lost-life mechanics that induce anxiety.
- [x] **Distress & Confusion Circuit Breaker**: If 3 consecutive errors or erratic rapid taps occur, the game automatically suspends and smoothly transitions into `DistressCalmingScreen`, displaying family photos and playing comforting voice reassurance notes.
- [x] **Gentle Idle Handling**: Unanswered reminders or tasks gently re-prompt once via regional voice before calmly fading back to the soothing Today screen.

---

## 3. End-to-End Test Matrix

| Flow | Expected Outcome | Verification |
| :--- | :--- | :--- |
| **1. Language & Role Selection** | Selects regional language (e.g. Assamese), chooses role (Patient/Family/Doctor), authenticates via Firebase Phone OTP | ✅ Verified |
| **2. Patient Setup** | Family member creates patient profile, sets routine, uploads doctor/hospital reference and family photos | ✅ Verified |
| **3. Patient "Today" Engine** | Screen automatically shows time-of-day greeting, single big 10-minute game button, and large SOS | ✅ Verified |
| **4. Cognitive Gaming Suite** | AI Adaptive Game Scheduler chooses appropriate game & difficulty (2/3/5 items) without patient menu navigation | ✅ Verified (All 9 Games) |
| **5. Incoming Call Reminders** | Scheduled medication alerts trigger full-screen pulsing incoming call overlay | ✅ Verified |
| **6. Family Dashboard & Nudge** | Caregivers monitor cognitive scores, adherence %, send real-time "Play with Grandpa" invites and nudges | ✅ Verified |
| **7. Doctor Clinical Portal** | Multi-patient triage across NER districts, longitudinal score graphs, clinical observations, family guidance push | ✅ Verified |
| **8. Offline Persistence** | Complete gameplay, reminder acknowledgment, and local queuing function seamlessly with no internet connection | ✅ Verified |
| **9. Tier 3 Kiosk Mode** | Android Screen Pinning API restricts device to SmritiSaathi with explicit caregiver consent | ✅ Verified |
