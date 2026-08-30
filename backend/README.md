# SmritiSaathi Backend

Firebase backend configuration for the dementia care companion app.

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    Firebase Services (Spark Plan)            │
├─────────────────────────────────────────────────────────────┤
│  Authentication (Phone/OTP)                                  │
│  ├── Patient login via phone verification                    │
│  └── Family/Doctor login via phone or email                  │
├─────────────────────────────────────────────────────────────┤
│  Cloud Firestore (Native Mode)                               │
│  ├── /patients/{patientId}                                   │
│  │   ├── basicInfo, dementiaTier, language                  │
│  │   ├── routine, emergencyContact                          │
│  │   ├── /familyLinks/{familyMemberId}                      │
│  │   ├── /gameResults/{sessionId}                           │
│  │   ├── /reminders/{reminderId}                            │
│  │   ├── /behavioralAlerts/{alertId}                        │
│  │   └── /reminiscenceContent/{contentId}                   │
│  ├── /users/{userId}                                         │
│  └── /doctors/{doctorId}                                     │
├─────────────────────────────────────────────────────────────┤
│  Cloud Storage                                                │
│  ├── /patients/{patientId}/photos/                           │
│  ├── /patients/{patientId}/voiceNotes/                       │
│  ├── /patients/{patientId}/medicalReports/                   │
│  └── /reminiscence/{patientId}/                              │
├─────────────────────────────────────────────────────────────┤
│  Cloud Messaging (FCM)                                       │
│  ├── Behavioral alerts → Family members                      │
│  ├── Reminders → Patient device                              │
│  └── Status updates → Caregivers                             │
└─────────────────────────────────────────────────────────────┘
```

## Firestore Data Model

### /patients/{patientId}

```javascript
{
  basicInfo: {
    name: string,
    age: number,
    gender: 'male' | 'female' | 'other',
    profilePhoto: string, // Storage URL
    diagnosisDate: timestamp,
    preferredLanguage: 'en' | 'hi' | 'bn' | 'ta' | 'te' | 'mr'
  },
  dementiaTier: 'mild' | 'moderate' | 'severe',
  medicalReportURLs: string[],

  routine: {
    wakeTime: string,        // "07:00"
    sleepTime: string,       // "22:00"
    napTimes: string[],      // ["14:00", "15:30"]
    mealTimes: {
      breakfast: string,     // "08:00"
      lunch: string,         // "13:00"
      dinner: string         // "20:00"
    },
    medicineTimes: string[]  // ["09:00", "21:00"]
  },

  emergencyContact: {
    name: string,
    phone: string,
    relationship: string
  },

  createdAt: timestamp,
  updatedAt: timestamp
}
```

### /patients/{patientId}/familyLinks/{familyMemberId}

```javascript
{
  relationship: 'spouse' | 'child' | 'sibling' | 'caregiver' | 'other',
  permissionLevel: 'read' | 'write' | 'admin',
  linkedAt: timestamp,
  fcmToken: string // For notifications
}
```

### /patients/{patientId}/gameResults/{sessionId}

```javascript
{
  gameType: 'memory_match' | 'word_recall' | 'face_recognition' | 'quiz',
  score: number,
  maxScore: number,
  timestamp: timestamp,
  difficultyLevel: 'easy' | 'medium' | 'hard',
  responseTimeMs: number,
  durationSeconds: number,
  correctAnswers: number,
  totalQuestions: number
}
```

### /patients/{patientId}/reminders/{reminderId}

```javascript
{
  type: 'medicine' | 'appointment' | 'task',
  title: string,
  description: string,
  scheduledTime: timestamp,
  status: 'pending' | 'taken' | 'skipped' | 'snoozed',
  respondedAt: timestamp,
  recurrence: 'once' | 'daily' | 'weekly' | 'custom',
  createdBy: string // userId of family member
}
```

### /patients/{patientId}/behavioralAlerts/{alertId}

```javascript
{
  type: 'wandering' | 'aggression' | 'confusion' | 'anxiety' | 'sleep_disturbance',
  severity: 'low' | 'medium' | 'high' | 'critical',
  description: string,
  timestamp: timestamp,
  location: { latitude: number, longitude: number }, // Optional
  acknowledged: boolean,
  acknowledgedBy: string, // userId
  acknowledgedAt: timestamp
}
```

### /patients/{patientId}/reminiscenceContent/{contentId}

```javascript
{
  type: 'photo' | 'voiceNote' | 'video',
  url: string, // Storage URL
  uploadedBy: string, // userId
  uploadedAt: timestamp,
  personTag: string[], // Names of people in content
  description: string,
  dateTaken: timestamp // Original date of photo/event
}
```

### /users/{userId}

```javascript
{
  role: 'patient' | 'family' | 'doctor',
  linkedPatientIds: string[],
  fcmToken: string,
  profile: {
    name: string,
    phone: string,
    email: string,
    profilePhoto: string
  },
  createdAt: timestamp
}
```

### /doctors/{doctorId}

```javascript
{
  profile: {
    name: string,
    specialization: string,
    hospital: string,
    phone: string,
    email: string
  },
  assignedPatientIds: string[],
  createdAt: timestamp
}
```

### /doctors/{doctorId}/clinicalNotes/{noteId}

```javascript
{
  patientId: string,
  date: timestamp,
  notes: string,
  assessment: string,
  recommendations: string[],
  followUpDate: timestamp
}
```

## Deployment Guide (100% FREE - Spark Plan)

### Prerequisites

1. Node.js 18+ installed
2. Firebase CLI installed globally:
   ```bash
   npm install -g firebase-tools
   ```

### Step-by-Step Setup

#### 1. Create Firebase Project

```bash
# Log in to Firebase
firebase login

# Create new project (or use existing)
# Go to https://console.firebase.google.com
# Click "Add project" → Enter "smritisaathi" → Select Spark plan
```

#### 2. Initialize Firebase in Project

```bash
cd backend

# Initialize Firebase
firebase init

# Select:
# ✅ Firestore
# ✅ Functions
# ✅ Storage
# ✅ Emulators (optional, for local testing)

# Choose "Use existing project" → Select your project
```

#### 3. Enable Required Services (via Console)

Go to Firebase Console for your project:

**Authentication:**
1. Authentication → Sign-in method
2. Enable "Phone" provider
3. Add test phone numbers for development (optional)

**Firestore:**
1. Firestore → Create database
2. Select "Start in Native mode"
3. Choose location (closest to users)
4. ⚠️ This will deploy empty security rules first - deploy your rules after

**Storage:**
1. Storage → Get started
2. Select same location as Firestore

#### 4. Deploy Configuration

```bash
# Deploy Firestore rules and indexes
firebase deploy --only firestore

# Deploy Storage rules
firebase deploy --only storage

# Install functions dependencies
cd functions
npm install
cd ..

# Deploy Cloud Functions
firebase deploy --only functions
```

#### 5. Get Configuration for Apps

```bash
# View project configuration
firebase apps:list

# For Android:
# Project settings → Your apps → Add app → Android
# Package name: com.smritisaathi.app
# Download google-services.json → Place in android/app/

# For iOS:
# Project settings → Your apps → Add app → iOS
# Bundle ID: com.smritisaathi.app
# Download GoogleService-Info.plist → Place in ios/SmritiSaathi/
```

### Free Tier Limits (Spark Plan)

| Service | Free Quota | Notes |
|---------|------------|-------|
| Authentication | 10,000 verifications/month | Phone OTP counts toward this |
| Firestore Reads | 50,000/day | Optimize queries, use caching |
| Firestore Writes | 20,000/day | Batch writes when possible |
| Firestore Deletes | 20,000/day | |
| Storage | 5 GB total | Compress images before upload |
| Storage Downloads | 1 GB/day | |
| Cloud Functions | 125,000 invocations/month | |
| Function Memory | 256 MB max | |
| Function Timeout | 1 minute max | |
| FCM Messages | Unlimited | No external API calls needed |

### Cost Optimization Tips

1. **Firestore Reads:**
   - Cache patient data locally on device
   - Use Firestore snapshots for real-time updates (counts as one read)
   - Paginate game results and behavioral alerts

2. **Firestore Writes:**
   - Batch related writes together
   - Use Cloud Functions to aggregate data daily/weekly

3. **Storage:**
   - Compress images before upload (target < 500KB per photo)
   - Use WebP format for photos
   - Limit voice note duration to 30 seconds

4. **Functions:**
   - Keep function execution time under 30 seconds
   - Use minimal dependencies

### Local Development with Emulators

```bash
# Start all emulators
firebase emulators:start

# Start specific emulators
firebase emulators:start --only firestore,functions

# Export emulator data
firebase emulators:export ./emulator-data

# Import emulator data
firebase emulators:start --import=./emulator-data
```

### Testing Security Rules

```bash
# Run rules tests (if you add tests)
cd functions
npm test

# Or use the Rules Playground in Firebase Console
```

## Security Rules Summary

### Access Matrix

| Resource | Patient Device | Family (Read) | Family (Write) | Doctor |
|----------|---------------|---------------|----------------|--------|
| Patient Profile | ✅ Read/Write | ✅ Read | ✅ Write | ✅ Read |
| Family Links | ❌ | ✅ Read | ✅ Write | ✅ Read |
| Game Results | ✅ Write | ✅ Read | ❌ | ✅ Read |
| Reminders | ✅ Update Status | ✅ Read | ✅ Write | ❌ |
| Behavioral Alerts | ✅ Write | ✅ Read | ❌ | ✅ Read |
| Reminiscence Content | ✅ Read | ✅ Read | ✅ Write | ❌ |
| Clinical Notes | ❌ | ✅ Read | ❌ | ✅ Write |

## Alternative Name Suggestions

- **MemoryMate** - Friendly, emphasizes companionship
- **RemindHer** - Playful, focuses on reminding
- **CareConnect** - Professional, emphasizes family connection
- **Sthira** - Sanskrit for "stable/steady" - mental stability theme
- **Yaad** - Hindi for "memory" - simple, memorable

## Cloud Functions Reference

### 1. onGameResultWritten
**Trigger:** Firestore onCreate (`patients/{patientId}/gameResults/{sessionId}`)

**Purpose:** Detect significant score drops in cognitive games and create behavioral alerts

**How it works:**
1. Fetches last 14 days of game results for same game type
2. Calculates rolling average score percentage
3. If current score drops 25%+ below average → creates `behavioralAlert`

**Free Tier:** ✅ Fully eligible

**Severity Levels:**
| Drop % | Severity |
|--------|----------|
| 50%+ | critical |
| 35%+ | high |
| 25%+ | medium |

---

### 2. checkOverdueReminders
**Trigger:** HTTPS Callable (called from app)

**Purpose:** Check for overdue reminders and notify family members

**⚠️ FREE TIER NOTE:**
Scheduled functions (using Cloud Scheduler/PubSub) require Blaze plan. This function uses an alternative approach:

**Free Tier Workaround:**
- Patient app calls this on app open
- Family app has "Check Overdue" button
- Alternatively, write to `patients/{patientId}/reminderChecks/` to trigger `processReminderCheck`

**Parameters:**
```javascript
{
  patientId: string,
  overdueMinutes: number  // default: 30
}
```

**Free Tier:** ✅ Fully eligible (when called from app)

---

### 3. generateReport
**Trigger:** HTTPS Callable

**Purpose:** Generate structured JSON report for PDF generation on client

**Parameters:**
```javascript
{
  patientId: string,
  reportType: 'weekly' | 'monthly' | 'yearly' | 'custom',
  startDate: string,  // ISO date (custom only)
  endDate: string     // ISO date (custom only)
}
```

**Returns:**
```javascript
{
  metadata: { reportId, patientId, reportType, dateRange },
  summary: {
    overallHealthScore,  // 0-100
    gameSessionsPlayed,
    reminderAdherenceRate,
    behavioralAlertsCount
  },
  gameResults: { sessions[], statistics{} },
  reminders: { items[], statistics{} },
  behavioralAlerts: { items[], statistics{} },
  recommendations: [{ priority, category, title, description }]
}
```

**Client-Side PDF Generation:**
- Android: Use `PdfDocument` or `iText` library
- iOS: Use `PDFKit` framework

**Free Tier:** ✅ Fully eligible

---

### 4. sendNudge
**Trigger:** HTTPS Callable

**Purpose:** Send real-time action to patient device via Firestore snapshot listener

**How it works:**
1. Family member calls function
2. Writes to `patients/{patientId}/pendingActions/{actionId}`
3. Patient app listens via `onSnapshot`
4. Also sends FCM notification for immediate delivery

**Action Types:**
| Type | Title | Purpose |
|------|-------|---------|
| start_game | 🎮 Time to Play! | Prompt patient to start cognitive game |
| view_reminder | 📋 Reminder Alert | Highlight specific reminder |
| call_family | 📞 Family Calling | Request call back |
| check_in | 👋 Check In | Request status confirmation |
| custom | Custom title | Free-form message |

**Parameters:**
```javascript
{
  patientId: string,
  actionType: 'start_game' | 'view_reminder' | 'call_family' | 'check_in' | 'custom',
  payload: {
    gameType?: string,      // for start_game
    reminderTitle?: string, // for view_reminder
    title?: string,         // for custom
    message?: string,       // for custom
    priority?: 'normal' | 'high'
  }
}
```

**Free Tier:** ✅ Fully eligible

---

## pendingActions Collection Schema

Added to support real-time nudges:

```
/patients/{patientId}/pendingActions/{actionId}
  - actionId: string
  - type: 'start_game' | 'view_reminder' | 'call_family' | 'check_in' | 'custom'
  - title: string
  - description: string
  - payload: object
  - status: 'pending' | 'received' | 'completed' | 'dismissed'
  - sentBy: string (userId)
  - sentByName: string
  - sentAt: timestamp
  - receivedAt: timestamp
  - completedAt: timestamp
  - expiresAt: timestamp (10 minutes)
```

**Patient App Implementation:**
```kotlin
// Android - Listen for pending actions
db.collection("patients/$patientId/pendingActions")
  .whereEqualTo("status", "pending")
  .addSnapshotListener { snapshot, e ->
    // Handle incoming nudge
  }
```

```swift
// iOS - Listen for pending actions
db.collection("patients/\(patientId)/pendingActions")
  .whereField("status", isEqualTo: "pending")
  .addSnapshotListener { querySnapshot, error in
    // Handle incoming nudge
  }
```

---

## Free Tier Quota Considerations

| Function | Reads/Invocation | Writes/Invocation | Notes |
|----------|------------------|-------------------|-------|
| onGameResultWritten | ~50 | 1 | Limited to 50 historical results |
| checkOverdueReminders | ~20 | 0 | Queries reminders + familyLinks |
| generateReport | ~150 | 0 | Limited to 50 items per collection |
| sendNudge | 2 | 1 | User check + action write |

**Daily Invocation Estimates (Spark Plan):**
- 50,000 reads/day → ~333 generateReport calls
- 20,000 writes/day → ~20,000 gameResults/alerts

---

## Deployment

```bash
cd backend/functions
npm install
cd ..
firebase deploy --only functions
```

**Functions deployed:**
- `onGameResultWritten` - Firestore trigger
- `checkOverdueReminders` - HTTPS callable
- `processReminderCheck` - Firestore trigger (alternative)
- `generateReport` - HTTPS callable
- `sendNudge` - HTTPS callable
- `acknowledgeNudge` - HTTPS callable
- `cleanupExpiredActions` - Firestore trigger
- `notifyFamilyOnAlert` - Firestore trigger
- `linkFamilyMember` - HTTPS callable
- `registerFCMToken` - HTTPS callable

---

## Next Steps

1. [ ] Add Firestore database seed data for testing
2. [ ] Set up Firebase App Check for security
3. [ ] Configure Firebase Crashlytics for error tracking
4. [ ] Set up Firebase Analytics for usage insights
5. [ ] Implement client-side PDF generation for reports
