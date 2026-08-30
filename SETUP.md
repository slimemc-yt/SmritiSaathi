# SmritiSaathi - SIH Grand Finale Evaluation & Demo Guide

**SmritiSaathi** is an AI-based cognitive gaming, memory assistance, and clinical triage platform designed specifically for elderly dementia patients in the North East Region (NER) of India, operating **100% on free-tier infrastructure**.

---

## 🌟 Quick Live Demo Flow for Judges (3-5 Minute Pitch)

### Step 1: Language Selection & Role Switcher
1. Launch the Android or iOS app.
2. Select your preferred regional language (e.g., **Assamese / অসমীয়া** or **English**). Notice the speaker button providing native voice samples for low-literacy elderly users.
3. On the Role Selection Screen, choose between:
   - 👵 **Patient Mode**: Voice-first, single-task Today screen with big 10-minute game button & always-visible SOS.
   - 👨‍👩‍👦 **Family Member Mode**: Caregiver dashboard with cognitive rolling averages, medication adherence %, remote voice nudges, and "Play With Grandpa" game invites.
   - 🩺 **Doctor / ASHA Worker Mode**: NER multi-district clinical triage, longitudinal cognitive trend graphs, and clinical guidance push tools.

---

### Step 2: Patient Experience & Cognitive Games Suite
1. Select **Patient**. The screen loads a soothing greeting with the patient's name (**Hemlata Devi**) and a single prominent **"Start Your Activity"** button.
2. Tap the activity button. The **AI Adaptive Game Scheduler** automatically launches the appropriate exercise (no confusing menus for the patient!):
   - **Card Matching**: Visual working memory & pattern matching.
   - **Face Recognition**: Identify uploaded family members (e.g., grandson Aarav).
   - **Daily Routine Ordering**: Sequencing morning tea, medicine, and evening walks.
   - **NER Familiar Images**: Assam, Meghalaya, and regional cultural landmarks (Kaziranga rhino, Majuli island).
   - **Shopping Basket Recall**: Grocery list memory challenge.
   - **Quick Recall Story**: Short narrative comprehension with Gemini AI proxy fallback.
   - **Life Stage Album**: Autobiographical memory journey.
   - **Guess Who's Speaking**: Voice note recognition from family members.
   - **Music & Folk Melody**: Regional folk song resonance.
3. **No-Guilt UX Demonstration**: Make 3 consecutive errors. Notice how the app never shows a red "fail" screen or penalty. Instead, it smoothly shifts into **Distress Calming Mode**, presenting a comforting family photo and reassuring voice note.

---

### Step 3: Incoming Call Style Medication Alert
1. Scheduled medicine alerts trigger a pulsing, incoming-phone-call overlay: *"📞 It's 8:00 PM — time for your evening medicine."*
2. Tap **"Taken ✅"** to instantly update caregiver adherence records in Firestore.
3. Tap **"I need help 🆘"** to immediately generate an urgent behavioral alert in the caregiver inbox.

---

### Step 4: Family Member Command Center & Remote Actions
1. Switch to **Family Member** role.
2. View real-time cognitive score trends (84% average) and medication adherence (92%).
3. Tap **"Send Nudge"** or **"Play With Grandpa"**:
   - Dispatches a real-time command to `/patients/{id}/pendingActions`.
   - The patient's device instantly pops up the personalized invitation in real-time.
4. View the **Clinical Progress Report** aggregating 30-day session metrics.

---

### Step 5: Doctor / Health Worker Portal
1. Switch to **Doctor / ASHA Worker** role.
2. View the district patient triage list with stage badges (Mild, Moderate, Severe).
3. Open a patient's detail view to inspect score distributions and medication adherence.
4. Type a note into **"Guidance to Push to Family App"** and tap **"Save & Push"**. The note immediately synchronizes to the family dashboard!

---

## 🛠️ Architecture & Free-Tier Services Breakdown

| Component | Technology | Free Tier Eligibility |
| :--- | :--- | :--- |
| **Android App** | Kotlin, Jetpack Compose, Hilt, Coroutines | Native Android SDK (Min API 24) |
| **iOS App** | Swift 5.9+, SwiftUI, AVSpeechSynthesizer | iOS 15.0+ |
| **Smartwatch** | Wear OS Compose, Health & Alert Sync | Android Wear OS SDK |
| **Database & Auth** | Firebase Phone Auth & Cloud Firestore | Firebase Spark Plan (Unlimited offline caching) |
| **Cloud Functions** | Node.js (2nd Gen Firebase Functions) | Spark / Free Quota Compatible |
| **AI Question Engine** | Google Gemini API (1.5 Flash) | 15 RPM / 1,500 RPD Free Tier |
| **Regional Voice** | Bhashini API (Govt of India) + On-Device TTS | Free API + Zero-cost Native Fallback |
| **Face Recognition** | Google ML Kit (Android) / Apple Vision (iOS) | 100% On-Device & Free |
