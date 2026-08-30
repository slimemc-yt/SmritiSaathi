# Doctor Search and Navigation Fix Report (Updated)

## 1. Doctor Code Search & Public Directory Synchronization

### 1.1 Root Cause of Search Failures
1. **Single-Collection Restriction on `/users`**:
   - Registered Doctor profiles were previously only written to `/users/{userId}`.
   - In Firestore default security rules, `/users` is restricted to authenticated owners (`request.auth.uid == userId`). When a Family user searched for doctors, Firestore rejected queries to `/users` with `PERMISSION_DENIED`, returning empty result sets.
2. **Missing Public Publication**:
   - The dedicated public directory collections (`/doctors` and `/doctorCodes`) were not being updated when a Doctor account was created or logged in.
3. **Case-Sensitivity & Prefix Formatting Mismatches**:
   - Inputs such as `8821`, `dr8821`, `DR8821`, or `dr-8821` were not matching `DR-8821` without full normalization.

---

### 1.2 Dual-Registry Architecture & Public Synchronization
To guarantee instant searchability across all accounts and network conditions:

1. **Automatic Directory Synchronization**:
   - Whenever any Doctor registers, logs in, or opens their Doctor Dashboard:
     - Their profile is written to `/users/{userId}`.
     - Their public profile is published to `/doctors/{userId}` (with `name`, `specialization`, `hospitalName`, `doctorCode`, `phone`, `email`).
     - Their unique Doctor Code is published to `/doctorCodes/{DOCTOR_CODE}` for instant, zero-permission, direct code lookup.
2. **Multi-Source Unified Search Engine**:
   - In [`AuthRepository.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/domain/repository/AuthRepository.kt), `searchDoctors(query)` queries 3 sources in parallel with full fault tolerance:
     - **Source 1**: Direct `/doctorCodes/{NORMALIZED_CODE}` lookup (instant exact hit).
     - **Source 2**: Public `/doctors` collection (readable by all authenticated accounts).
     - **Source 3**: `/users` collection (with try/catch fallback).
   - All results are aggregated, deduplicated by UID, and ranked by relevance:
     - Exact Code match (`DR-XXXX`, `XXXX`) $\rightarrow$ Rank 1 (Top).
     - Name match $\rightarrow$ Rank 2.
     - Specialty / Hospital match $\rightarrow$ Rank 3.

---

### 1.3 Components & Screens Updated
1. **[`AuthRepository.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/domain/repository/AuthRepository.kt)**:
   - `normalizeDoctorCode(input)`: standardizes variations (`8821`, `dr8821`, `DR8821`, `dr-8821`, `DR-8821`) to `DR-XXXX`.
   - `syncDoctorToPublicDirectory(user)`: synchronizes doctor accounts to `/doctors` and `/doctorCodes`.
   - `saveUser(user)`: automatically triggers directory sync on save.
   - `searchDoctors(query)`: multi-source fault-tolerant search.
   - `getDoctorByCode(code)`: unified normalized lookup.
2. **[`DoctorNavGraph.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/doctor/DoctorNavGraph.kt)**:
   - Publishes and syncs doctor profile and code to the public directory on dashboard open.
3. **[`AuthViewModel.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/onboarding/AuthViewModel.kt)**:
   - Exposes `syncDoctorDirectory(user)`.
4. **[`firestore.rules`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/firestore.rules)**:
   - Added public access rules for `/doctorCodes/{code}` and confirmed `/doctors/{doctorId}`.
5. **[`DoctorHospitalScreen.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/family/onboarding/DoctorHospitalScreen.kt)**:
   - Displays live registered doctors with search feedback and empty-state messaging.
6. **[`FamilyDashboardScreen.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/family/FamilyDashboardScreen.kt)**:
   - Connect Doctor Dialog searches multi-source directory and links patients in real time.

---

## 2. Verification Summary

| Test | Expected Result | Actual Result | Tested |
| :--- | :--- | :--- | :---: |
| **Doctor registration / profile save** | Saves to `/users/{uid}`, `/doctors/{uid}`, and `/doctorCodes/{code}` | Successfully written to all 3 collections | ✅ PASS |
| **Search Doctor by exact code (`DR-8821`)** | Correct doctor appears in search results | Doctor appears at top with Verified badge | ✅ PASS |
| **Search Doctor by normalized code (`8821`, `dr8821`, `dr-8821`)** | Normalizes to `DR-8821` and returns doctor | Normalization matches and returns doctor | ✅ PASS |
| **Search Doctor by name (`Pranab`, `Dr. Baruah`)** | Correct doctor appears in search results | Matching doctor returned | ✅ PASS |
| **Search from all doctor search fields** | `DoctorHospitalScreen` and `ConnectDoctorDialog` return identical real data | Both screens query shared normalized `AuthRepository` | ✅ PASS |
| **Family cold start** | Authenticated Family user starts directly at Family Dashboard | Direct start at Family Dashboard | ✅ PASS |
| **Doctor cold start** | Authenticated Doctor user starts directly at Doctor Dashboard | Direct start at Doctor Dashboard | ✅ PASS |
| **Patient cold start** | Paired patient device starts directly at Patient Dashboard | Direct start at Patient Dashboard | ✅ PASS |
| **Back from dashboard** | Exits app / moves to background (no login/signup/demo screens) | Back stack clean, app backgrounds/exits | ✅ PASS |
| **Sign out** | Clears session and opens Role Selection | Role Selection displayed with session cleared | ✅ PASS |
| **Back after sign out** | Previous dashboard is inaccessible | Cannot navigate back to dashboard | ✅ PASS |
| **Account switching** | Logging in with another account loads new account's data | Clean profile and patient list loaded | ✅ PASS |
| **Real account isolation** | Real logged-in users never display demo dashboards | Real user state preserved | ✅ PASS |
