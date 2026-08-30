# SmritiSaathi

A comprehensive dementia care companion app designed to support patients through cognitive exercises, routine management, and family connection.

> **Note:** SmritiSaathi is a working name. Alternative suggestions: MemoryMate, Yaad, Sthira, CareConnect

## Project Structure

```
smritisaathi/
├── backend/           # Firebase configuration & Cloud Functions
│   ├── firestore.rules
│   ├── storage.rules
│   ├── firebase.json
│   └── functions/     # Cloud Functions source
│
├── android/           # Native Android app (Kotlin + Jetpack Compose)
├── ios/               # Native iOS app (Swift + SwiftUI)
├── wearos/            # Wear OS companion app
│
└── docs/              # Project specification & documentation
```

## Core Features

### For Patients
- Cognitive games (memory match, word recall, face recognition)
- Daily routine tracking and reminders
- Reminiscence therapy with photos and voice notes
- Simplified, accessible UI for cognitive ease

### For Family Members
- Real-time behavioral alerts
- Game performance monitoring
- Remote reminder management
- Photo/voice note uploads for reminiscence

### For Doctors
- Read-only access to cognitive assessment data
- Behavioral pattern analysis
- Clinical notes documentation
- Patient progress tracking

## Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Firebase (Auth, Firestore, Storage, Functions, FCM) |
| Android | Kotlin, Jetpack Compose, MVVM, Hilt |
| iOS | Swift, SwiftUI, Combine |
| Wear OS | Kotlin, Compose for Wear OS |
| CI/CD | GitHub Actions (planned) |

## Getting Started

### Prerequisites
- Node.js 18+
- Android Studio Hedgehog+
- Xcode 15+ (for iOS development)
- Firebase account (free Spark plan works)

### Setup

1. **Clone the repository**
   ```bash
   git clone <repo-url>
   cd smritisaathi
   ```

2. **Configure Firebase Backend**
   ```bash
   cd backend
   # Follow detailed instructions in backend/README.md
   ```

3. **Set up Android app**
   ```bash
   cd android
   # Place google-services.json in app/
   ./gradlew build
   ```

4. **Set up iOS app**
   ```bash
   cd ios
   # Place GoogleService-Info.plist in SmritiSaathi/
   open SmritiSaathi.xcodeproj
   ```

## Firebase Services (Free Tier)

All features work on the **Firebase Spark plan** (free):

- Authentication (Phone/OTP)
- Cloud Firestore (Native mode)
- Cloud Storage (photos/voice notes/medical reports)
- Cloud Functions (limited invocations)
- Cloud Messaging (push notifications)

See `backend/README.md` for detailed deployment instructions.

## Data Model

```
/patients/{patientId}
  ├── basicInfo, dementiaTier, language
  ├── routine: { wakeTime, sleepTime, napTimes[], mealTimes, medicineTimes }
  ├── emergencyContact
  │
  ├── /familyLinks/{familyMemberId}
  │   └── relationship, permissionLevel
  │
  ├── /gameResults/{sessionId}
  │   └── gameType, score, timestamp, difficultyLevel
  │
  ├── /reminders/{reminderId}
  │   └── type, scheduledTime, status, respondedAt
  │
  ├── /behavioralAlerts/{alertId}
  │   └── type, severity, description, timestamp
  │
  └── /reminiscenceContent/{contentId}
      └── type, uploadedBy, personTag, url

/users/{userId}
  └── role, linkedPatientIds[]

/doctors/{doctorId}
  ├── assignedPatientIds[]
  └── /clinicalNotes/{noteId}
```

## Security Model

- Patient data accessible only to linked family members and assigned doctors
- Patient devices have write-only access to gameResults, reminders status, and behavioralAlerts
- Doctors can read behavioral data but cannot modify patient records
- Family permission levels: `read`, `write`, `admin`

## Contributing

This is a hackathon project for SIH (Smart India Hackathon). Team contributions welcome.

## License

[To be determined]

---

Built with ❤️ for dementia patients and their caregivers
