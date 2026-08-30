# Project Specification: SmritiSaathi

## Overview

SmritiSaathi is a comprehensive mobile application designed to support dementia patients and their caregivers. The app provides cognitive exercises, routine management, behavioral monitoring, and family connectivity features.

## Target Users

1. **Patients** - Individuals with mild to moderate dementia
2. **Family Members** - Primary caregivers and family support network
3. **Doctors** - Healthcare providers monitoring patient progress

## Core Features

### 1. Cognitive Games Module

**Purpose:** Slow cognitive decline through regular mental exercises

**Games:**
- Memory Match - Card matching game with personalized photos
- Word Recall - Vocabulary and language exercises
- Face Recognition - Identify family members from photos
- Quiz - Personalized questions about patient's life

**Data Tracked:**
- Score, response time, difficulty level
- Timestamp and session duration
- Error patterns for analysis

### 2. Routine Management

**Purpose:** Maintain daily structure and reduce confusion

**Features:**
- Wake/sleep time reminders
- Meal time notifications
- Medicine reminders with confirmation
- Nap scheduling
- Custom task reminders

**Family Integration:**
- Remote reminder creation and management
- Confirmation notifications to caregivers
- Missed reminder alerts

### 3. Behavioral Monitoring

**Purpose:** Early detection of concerning behavioral changes

**Monitored Behaviors:**
- Wandering (GPS-based alerts)
- Sleep pattern changes
- Missed medications
- Activity level changes
- Aggression or anxiety indicators

**Alert System:**
- Real-time notifications to family
- Severity classification
- Doctor visibility for clinical assessment

### 4. Reminiscence Therapy

**Purpose:** Trigger positive memories and reduce anxiety

**Content Types:**
- Photos with person tagging
- Voice notes from family members
- Videos of important life events

**Features:**
- Family member uploads remotely
- Simple playback interface for patients
- Tagging and search capabilities

### 5. Family Dashboard

**Purpose:** Keep caregivers informed and connected

**Features:**
- Real-time patient status
- Game performance trends
- Behavioral alert history
- Reminder management interface
- Photo/voice note upload

### 6. Doctor Portal

**Purpose:** Clinical monitoring and documentation

**Features:**
- Read-only access to cognitive data
- Behavioral pattern visualization
- Clinical notes documentation
- Patient comparison views
- Follow-up scheduling

## Technical Requirements

### Platform Support
- Native Android (Kotlin + Jetpack Compose)
- Native iOS (Swift + SwiftUI)
- Wear OS companion app

### Backend
- Firebase (free Spark plan)
- Offline-first architecture
- Real-time sync

### Security
- Role-based access control
- Encrypted data transmission
- HIPAA-compliant design principles

### Accessibility
- Large text options
- High contrast mode
- Voice feedback
- Simple navigation
- Error forgiveness

## Data Model

See `backend/README.md` for complete Firestore schema.

## Development Phases

### Phase 1: Core Infrastructure (Week 1-2)
- [ ] Firebase project setup
- [ ] Authentication flow
- [ ] Basic data models
- [ ] Security rules

### Phase 2: Patient App (Week 3-4)
- [ ] Dashboard UI
- [ ] Cognitive games
- [ ] Reminders system
- [ ] Reminiscence viewer

### Phase 3: Family App (Week 5-6)
- [ ] Family dashboard
- [ ] Remote management
- [ ] Alert system
- [ ] Content upload

### Phase 4: Doctor Portal (Week 7-8)
- [ ] Analytics views
- [ ] Clinical notes
- [ ] Reporting

### Phase 5: Wear OS (Week 9-10)
- [ ] Simplified reminders
- [ ] Quick alerts
- [ ] Health metrics

## Success Metrics

1. **Patient Engagement**
   - Daily app usage > 30 minutes
   - Game completion rate > 80%
   - Reminder acknowledgment rate > 90%

2. **Caregiver Satisfaction**
   - Reduced anxiety (survey-based)
   - Improved communication with patient
   - Time saved on routine management

3. **Clinical Outcomes**
   - Slower cognitive decline vs control group
   - Fewer emergency incidents
   - Better medication adherence

## Privacy & Ethics

- Patient consent for all data collection
- Family access requires patient approval
- Doctor access requires both patient and family consent
- Data retention policies aligned with medical records requirements
- Right to data deletion

## Alternative Names Considered

| Name | Meaning | Pros | Cons |
|------|---------|------|------|
| SmritiSaathi | "Memory Companion" (Sanskrit) | Culturally relevant, meaningful | Longer name |
| MemoryMate | Direct, friendly | Easy to remember | Generic |
| Yaad | "Memory" (Hindi) | Short, simple | May not convey purpose |
| Sthira | "Stable" (Sanskrit) | Mental stability theme | Less intuitive |
| CareConnect | Professional | Clear purpose | Less unique |

---

*Document Version: 1.0*
*Last Updated: August 2026*
