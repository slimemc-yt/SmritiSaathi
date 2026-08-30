# Smart India Hackathon 2026 — Slide 3: Technical Approach
## Project: SmritiSaathi (স্মৃতিসাথী) — Dementia Care Companion

This guide gives you the **exact text, layout structure, flowchart design, and PowerPoint / Google Slides setup** for your Slide 3 ("TECHNICAL APPROACH"), formatted to look like a clean, well-organized presentation made manually by a student team.

An interactive preview file is saved at [`slide3_technical_approach.html`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/slide3_technical_approach.html).

---

## 📐 Slide Layout Breakdown (16:9 Standard Template)

```
+---------------------------------------------------------------------------------------------------+
|  (Team Name)                  TECHNICAL APPROACH                  [SIH 2026 LOGO]                 |
+------------------------------------+--------------------------------------------------------------+
|  Hardware & Software               |  FLOW CHART                                                  |
|                                    |  +----------------+  +----------------+  +----------------+  |
|  • Android / Kotlin                |  | 👴 Patient App |  | 👨‍👩‍👦 Family App  |  | 🩺 Doctor Portal| |
|    (Jetpack Compose)               |  +-------+--------+  +-------+--------+  +-------+--------+  |
|    Reason: Material 3, elderly-    |          |                   |                   |           |
|    friendly large touch targets.   |          v                   v                   v           |
|                                    |  +--------------------------------------------------------+  |
|  • Firebase Authentication         |  |   🔥 Firebase Spark Backend (Firestore & Auth)         |  |
|    Reason: Google Sign-in &        |  +---------------------------+----------------------------+  |
|    zero-password 6-digit pairing.  |                              |                               |
|                                    |                              v                               |
|  • Google Cloud Firestore          |  +--------------------------------------------------------+  |
|    Reason: Real-time sync for      |  |   ✨ Google Gemini 1.5 Flash AI Engine                 |  |
|    instant SOS alerts & med logs.  |  +--------------------------------------------------------+  |
|                                    |                                                              |
|  • Google Gemini 1.5 Flash         |  +-----------------------------+  +-----------------------+  |
|    Reason: AI cognitive scoring.   |  | GitHub link: (url)          |  | Above 85% of the      |  |
|                                    |  | YouTube video: (link)       |  | prototype is completed|  |
|  • Android On-Device TTS           |  +-----------------------------+  +-----------------------+  |
+------------------------------------+--------------------------------------------------------------+
|  🌐 SIH 2026                       @SIH Idea submission- Template                               3 |
+---------------------------------------------------------------------------------------------------+
```

---

## 📝 Exact Content to Paste into Your Slide

### 1. Header (Top)
- **Top-Left Oval Badge:** `[Your Team Name]` (e.g. `SmritiSaathi Team` / `Amateur Techies`)
- **Center Title:** `TECHNICAL APPROACH` (Font: Times New Roman or Arial, Bold, 32–36pt)
- **Top-Right:** Official SIH 2026 Logo

---

### 2. Left Column: Hardware & Software (Tech Stack & Reasons)

**Title:** `Hardware & Software` (Font: Arial / Calibri, Bold, 18pt)

Paste these bullet points (Font: Arial / Calibri, Regular, 12–13pt):

* **Android 14 / Kotlin (Jetpack Compose):** Native mobile client using Material 3, Clean MVVM architecture, and elderly-friendly large touch targets (48–80dp+) tailored for dementia ergonomics.
* **Firebase Authentication:** Google OAuth 2.0 Sign-In for Caregivers & Doctors; zero-password 6-digit numeric anonymous pairing for Patients to eliminate login anxiety.
* **Google Cloud Firestore:** Real-time reactive snapshot synchronization (`Kotlin Flow`) for instant SOS alerts, medication compliance tracking, and multi-user data sharing.
* **Google Gemini 1.5 Flash AI:** Synthesizes raw gameplay telemetry to compute multi-domain cognitive scores (Memory, Attention, Speed) and generates dementia-safe daily routines.
* **Android On-Device TTS:** Paced at 0.85x speed for gentle voice reminders and agitation de-escalation in 9 North East regional languages (Assamese, Bengali, Mizo, etc.).
* **Android Exact AlarmManager:** Hardware-level reliable medication alarms that ring even when the app is closed or offline.
* **Jetpack DataStore:** UID-scoped local storage ensuring fast offline boot (<1s) and data privacy across shared family devices.

---

### 3. Right Column: FLOW CHART (System Architecture & Tri-Party Data Flow)

**Title:** `FLOW CHART` (Font: Arial, Bold, Underlined, 18pt, Blue `#1F4E79`)

#### Step A: Top 3 Role Boxes (Horizontal Row)

1. **Patient Device (Tablet / Phone)** — *Border/Fill: Soft Orange / Peach (`#FFEEDB`)*
   * Zero-Password 6-Digit Pairing
   * 9 Cultural Brain Games
   * Daily Routine & Medication Alarms
   * One-Touch Distress / SOS Calming
   * 0.85x Paced Voice Assistant (TTS)

2. **Family / Caregiver App** — *Border/Fill: Soft Blue (`#E8F4FD`)*
   * 8-Step Patient Onboarding Wizard
   * Real-time Behavioral Alerts Inbox (SOS / Missed Meds)
   * Medication & Routine Schedule Manager
   * Reminiscence Photo Album Upload
   * "Play with Grandpa" Remote Game Invites

3. **Doctor / Healthcare Portal** — *Border/Fill: Soft Green (`#EAFAF1`)*
   * 3-Tier Clinical Risk Triage (High Risk, Attention, Stable)
   * Longitudinal Cognitive Trend Graphs
   * Medication Adherence Tracking (%)
   * Remote Cognitive Game Domain Assignment
   * Clinical Observation Notes Sync

---

#### Step B: Middle Backend & AI Boxes

1. **🔥 Firebase Spark Cloud Backend (Free-Tier Hub)** — *Border/Fill: Soft Warm Yellow (`#FFF8E1`)*
   * **Firebase Auth:** Google OAuth 2.0 + Anonymous Device Tokens
   * **Cloud Firestore DB:** Real-time collections (`/patients`, `/gameResults`, `/behavioralAlerts`, `/clinicalNotes`)
   * **Offline Fallback:** On-device local caching via Jetpack DataStore

2. **✨ Google Gemini 1.5 Flash AI Engine** — *Border/Fill: Soft Purple (`#F3E5F5`)*
   * **Cognitive Assessment:** Analyzes response times, error patterns, and memory domain scores.
   * **Dynamic Routine Generator:** Tailors daily routines based on dementia stage & regional culture.

---

#### Step C: Flow Connectors / Arrows (How they work together)

* **Patient $\rightarrow$ Firebase:** Uploads live game telemetry (mistakes, reaction time), medication completions, and one-touch SOS triggers.
* **Caregiver $\leftrightarrow$ Firebase:** Downloads real-time alerts and cognitive graphs; uploads patient schedules, emergency contacts, and family photos.
* **Doctor $\leftrightarrow$ Firebase:** Queries assigned patients; reviews longitudinal cognitive trends; writes clinical observation notes.
* **Firebase $\leftrightarrow$ Gemini AI:** Sends raw game performance data $\rightarrow$ Gemini computes 4-domain scores & clinical insights $\rightarrow$ saves results to `/cognitiveAssessments`.
* **Caregiver $\rightarrow$ Patient (via Firebase):** Sends `PLAY_INVITE` which rings the patient's tablet like a call to play a game together.

---

#### Step D: Bottom Link & Status Boxes

* **Left Box (Grey/Blue Fill):**
  * **GitHub link:** `https://github.com/your-username/SmritiSaathi`
  * **YouTube video:** `Link to 3-Min Prototype Demo`
* **Right Box (Light Blue Fill):**
  * **Status Text (Red, Bold):** `Above 85% of the prototype is completed`

---

## 💡 Quick Tips for Making it Look Authentically Student-Made

1. **Use Standard Fonts:** Stick to **Arial**, **Calibri**, or **Segoe UI** for body text and **Times New Roman** or **Arial** for the slide title.
2. **Simple Colored Rectangles:** In PowerPoint or Google Slides, insert standard rounded rectangles with 1.5pt solid borders and pastel fills (Peach for Patient, Blue for Family, Green for Doctor, Yellow for Firebase, Purple for AI).
3. **Straight Line Connectors:** Use PowerPoint's built-in connector arrows (`Insert > Shapes > Line Arrow`) with small label callouts like *"Telemetry / SOS"*, *"Sync & Alerts"*, *"Clinical Notes"*.
4. **Dividing Line:** Insert a vertical dashed line (`Shape Outline > Dashes`) between the left text list and the right flowchart.
5. **No AI Clutter:** Avoid 3D glassmorphic spheres, glowing neon halos, or generic stock illustrations. Clean boxes with crisp, accurate bullet points convey technical competence to hackathon judges.
