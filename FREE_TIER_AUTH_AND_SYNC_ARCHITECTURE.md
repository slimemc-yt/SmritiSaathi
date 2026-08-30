# SmritiSaathi - Free-Tier Authentication & Multi-Account Synchronization Architecture

## 1. Executive Summary

This architecture defines the zero-cost, multi-device synchronization and authentication framework for **SmritiSaathi**. It eliminates all dependencies on paid Firebase services (Blaze plan / 2nd gen Cloud Functions / Paid Storage buckets / SMS phone carrier verification) by using **Google Sign-In**, **Anonymous Device Pairing**, **Canonical Cloud Firestore synchronization**, and **Google AI Studio Free Tier (Gemini)**.

---

## 2. Recommended Architecture Diagram

```
                              ┌───────────────────────────────────┐
                              │     FAMILY MEMBER A (Primary)     │
                              │     • Google Auth (Permanent UID) │
                              │     • Full Caregiver Control      │
                              └─────────────────┬─────────────────┘
                                                │
                                                │ (Creates / Manages)
                                                ▼
┌───────────────────────────┐         ┌───────────────────┐         ┌───────────────────────────┐
│     FAMILY MEMBER B       │         │  CANONICAL RECORD │         │  DOCTOR / HEALTH WORKER   │
│     (Secondary Caregiver) │         │                   │         │  • Google Auth (Doctor UID│
│ • Google Auth             ├────────►│ /patients/{id}    │◄────────┤  • Assigned Patient List  │
│ • Joined via Family Code  │ (Sync)  │                   │ (Sync)  │  • Read Trends / Notes    │
│   (e.g., "FAM-4912")      │         │ Real-time Live DB │         │  • Linked via Doctor Code │
└───────────────────────────┘         └─────────▲─────────┘         │    (e.g., "DOC-8821")     │
                                                │                   └───────────────────────────┘
                                                │ (Sync: Game Results,
                                                │  Medicine Status, SOS)
                                                │
                              ┌─────────────────┴─────────────────┐
                              │          PATIENT DEVICE           │
                              │ • Paired ONCE via 6-digit Code    │
                              │ • Anonymous Firebase Auth Session │
                              │ • Bound to deviceLinks/{deviceUid}│
                              │ • NEVER logs in again (Auto-boot) │
                              └───────────────────────────────────┘
```

---

## 3. Authentication & Account Provisioning

### 3.1 Family Member & Doctor Authentication
- **Mechanism**: Google Sign-In via Firebase Auth.
- **Why**: 100% Free on Spark plan, zero SMS cost, zero carrier latency, permanent Google UID.
- **User Document** (`/users/{uid}`):
  ```json
  {
    "uid": "google_auth_uid_123",
    "role": "family", // or "doctor"
    "displayName": "Aarav Sharma",
    "email": "aarav@gmail.com",
    "photoUrl": "https://lh3.googleusercontent.com/...",
    "linkedPatientIds": ["patient_789"],
    "doctorInfo": {
      "hospitalName": "Guwahati Medical College",
      "specialty": "Neurology",
      "doctorCode": "DOC-8821",
      "isVerified": true
    },
    "createdAt": 1700000000000
  }
  ```

### 3.2 Patient Account Architecture (Option B: Device-Bound Anonymous Session)
- **Setup Flow**:
  1. Family Member creates the patient during onboarding. Firestore generates `/patients/{patientId}` with `pairingCode: "842913"`.
  2. On Patient's phone: Open app → Select "Patient" → Enter 6-digit code.
  3. Patient App authenticates anonymously (`auth.signInAnonymously()`).
  4. App registers device UID under `/patients/{patientId}/deviceLinks/{patientDeviceUid}`.
  5. `patientId` is stored in Android `DataStore`.
  6. Patient **never sees a login screen again**. App auto-boots directly to the single-task `PatientTodayScreen`.

---

## 4. Canonical Firestore Data Model

```
/users/{userId}
  ├── uid: String
  ├── role: "family" | "doctor" | "patient"
  ├── displayName: String
  ├── email: String
  ├── photoUrl: String
  ├── linkedPatientIds: List<String>
  └── doctorInfo: Map (Doctor only)

/patients/{patientId}
  ├── id: String
  ├── name: String
  ├── age: Int
  ├── gender: String
  ├── dementiaTier: 1 | 2 | 3
  ├── preferredLanguage: "as" | "hi" | "en" | ...
  ├── pairingCode: "842913"
  ├── familyInviteCode: "FAM-4912"
  ├── routine: { wakeTime, sleepTime, napTimes[], mealTimes[], medicineTimes[] }
  ├── emergencyContact: { name, relationship, phone }
  ├── primaryCaregiverUid: String
  │
  ├── /familyLinks/{familyUid}
  │     ├── permissionLevel: "PRIMARY_CAREGIVER" | "CAREGIVER" | "VIEWER"
  │     └── relationship: "Son"
  │
  ├── /deviceLinks/{deviceUid}
  │     ├── deviceUid: String
  │     └── deviceName: "Samsung Galaxy M14"
  │
  ├── /assignedDoctors/{doctorUid}
  │     ├── doctorName: "Dr. Baruah"
  │     └── assignedAt: Long
  │
  ├── /gameResults/{sessionId}
  │     ├── gameType: "CARD_MATCHING"
  │     ├── score: 85
  │     ├── difficultyLevel: 2
  │     ├── responseTimeMs: 14200
  │     └── timestamp: Long
  │
  ├── /reminders/{reminderId}
  │     ├── title: "Morning BP Tablet"
  │     ├── scheduledTime: "8:30 AM"
  │     └── status: "TAKEN" | "PENDING" | "SNOOZED" | "HELP_REQUESTED"
  │
  ├── /behavioralAlerts/{alertId}
  │     ├── type: "SCORE_DROP" | "MEDICATION_HELP" | "SOS"
  │     ├── severity: "URGENT" | "MEDIUM" | "LOW"
  │     └── timestamp: Long
  │
  ├── /pendingActions/{actionId}  <-- Real-time Family Nudges & Game Invites
  │     ├── type: "PLAY_INVITE" | "NUDGE" | "TRIGGER_CALMING"
  │     ├── senderName: "Aarav (Grandson)"
  │     ├── targetGame: "CARD_MATCHING"
  │     └── processed: Boolean
  │
  └── /clinicalNotes/{noteId}
        ├── doctorUid: String
        ├── noteText: "Patient showed good response times."
        └── guidanceForFamily: "Maintain daily routine walking."
```

---

## 5. Free-Tier Replacement for Cloud Functions & Storage

| Legacy Cloud Feature | Free-Tier Replacement | Implementation |
| :--- | :--- | :--- |
| `sendNudge` Function | Direct Firestore Write | Family app writes to `/pendingActions`; Patient app listens in real-time. |
| `onGameResultWritten` | Client-Side Evaluation | Analyzed client-side on game completion; writes to `/behavioralAlerts` if scores drop. |
| `onReminderOverdue` | Android `AlarmManager` | Native exact alarm fires reliably even when phone is locked or offline. |
| `generateReport` | Client-Side `PdfDocument` | Android native PDF graphics rendering. |
| `geminiQuizProxy` | Direct Google GenAI SDK | Free Google AI Studio API key (15 RPM / 1500 RPD) with 50+ item local procedural fallback bank. |
| Paid Storage Buckets | Bundled Assets & CDN | High-definition regional game assets bundled locally; avatar selector for family profiles. |

---

## 6. Firestore Security Rules (Free-Tier Compatible)

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    function isAuthenticated() { return request.auth != null; }
    function isPatientDevice(pId) {
      return isAuthenticated() && exists(/databases/$(database)/documents/patients/$(pId)/deviceLinks/$(request.auth.uid));
    }
    function isLinkedFamily(pId) {
      return isAuthenticated() && exists(/databases/$(database)/documents/patients/$(pId)/familyLinks/$(request.auth.uid));
    }
    function isAssignedDoctor(pId) {
      return isAuthenticated() && exists(/databases/$(database)/documents/patients/$(pId)/assignedDoctors/$(request.auth.uid));
    }

    match /users/{userId} {
      allow read: if isAuthenticated();
      allow write: if isAuthenticated() && request.auth.uid == userId;
    }

    match /patients/{patientId} {
      allow read: if isLinkedFamily(patientId) || isAssignedDoctor(patientId) || isPatientDevice(patientId);
      allow create: if isAuthenticated();
      allow update: if isLinkedFamily(patientId) || isPatientDevice(patientId);
      allow delete: if isLinkedFamily(patientId);

      match /{allSubcollections=**} {
        allow read: if isLinkedFamily(patientId) || isAssignedDoctor(patientId) || isPatientDevice(patientId);
        allow write: if isLinkedFamily(patientId) || isPatientDevice(patientId) || isAssignedDoctor(patientId);
      }
    }
  }
}
```
