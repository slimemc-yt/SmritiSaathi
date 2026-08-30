# Doctor Dashboard Audit & Route Analysis

**Date:** August 29, 2026  
**Status:** Audit Complete — Separate Doctor Dashboard **FOUND & VERIFIED**

---

## 1. Conclusion

> **A separate Doctor Dashboard ALREADY EXISTS and is fully implemented in the codebase.**

It is a genuinely distinct clinical dashboard designed specifically for doctors and healthcare workers. It is currently unreachable or bypassed under specific login conditions due to role resolution and session caching bugs in the authentication layer (`AuthRepository.kt`, `AuthViewModel.kt`, and `SplashScreen.kt`).

---

## 2. Evidence & Existing Doctor Implementation

The following files contain the existing, dedicated Doctor Dashboard implementation:

| File | Type | Implementation Details |
| :--- | :--- | :--- |
| [`ui/doctor/DoctorDashboardScreen.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/doctor/DoctorDashboardScreen.kt) | **UI Screen (298 lines)** | **Clinical Triage & Oversight Hub**: Features Dr. Pranab Baruah MD header, Doctor Referral Code card (`DOC-XXXX`), patient search bar, triage categorization (`Stable`, `Moderate Monitor`, `High Care Tier`), and Firestore listener for assigned patients (`repository.getPatientsByDoctorId(doctorId)`). |
| [`ui/doctor/DoctorPatientDetailScreen.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/doctor/DoctorPatientDetailScreen.kt) | **UI Screen (282 lines)** | **Clinical Patient Oversight**: Displays longitudinal cognitive domain scores (Memory, Attention, Problem Solving), medication adherence rate, clinical observation timeline, and an "Add Clinical Note" FAB. |
| [`ui/doctor/DoctorNavGraph.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/doctor/DoctorNavGraph.kt) | **Navigation Graph (98 lines)** | Internal nested `NavHost` routing between `doctor_patients` (`DoctorDashboardScreen`) and `doctor_patient_detail/{patientId}` (`DoctorPatientDetailScreen`). |
| [`domain/model/ClinicalNote.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/domain/model/ClinicalNote.kt) | **Domain Model** | Clinical note model with `doctorId`, `doctorName`, `observation`, `familyGuidance`, and `timestamp`. |
| [`domain/repository/PatientRepository.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/domain/repository/PatientRepository.kt#L341-L356) | **Data Repository** | Real-time Firestore snapshot flow `getPatientsByDoctorId(doctorId)` querying `patients` where `assignedDoctorId == doctorId`. |

### Comparison: Doctor Dashboard vs. Family Dashboard

| Feature | Doctor Dashboard (`DoctorDashboardScreen.kt`) | Family Dashboard (`FamilyDashboardScreen.kt`) |
| :--- | :--- | :--- |
| **Primary Focus** | Multi-patient clinical triage & cognitive risk categorization | Single patient daily care & activity monitoring |
| **Header** | "Clinical Triage & Oversight — Dr. Pranab Baruah, MD" | "Caregiver Command Center — Caring for [Patient]" |
| **Key Actions** | Search patients, review cognitive metrics, add clinical notes | "Play with Grandpa", "Send Gentle Nudge", "Calm Mode" |
| **Navigation Target** | `DoctorPatientDetailScreen` (clinical graphs, doctor notes) | `PlayWithGrandpaScreen`, `BehavioralAlertsInboxScreen`, `FamilyRemindersManagerScreen`, `FamilyReminiscenceManagerScreen` |
| **Color Theme** | Purple accent (`DoctorColor = 0xFF8B5CF6`) | Warm Teal / Orange accent |

---

## 3. Current Navigation Flow Analysis

The intended routing flow in [`SmritiSaathiNavigation.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/navigation/SmritiSaathiNavigation.kt#L97-L106) is:

```text
RoleSelectionScreen (Select "Doctor")
       ↓
LoginScreen (role = UserRole.DOCTOR)
       ↓
Google Sign-In / Demo Login
       ↓
onLoginSuccess(role, isNewUser)
       ↓
when (role) {
    UserRole.DOCTOR -> Screen.DoctorHome.route  ──>  DoctorNavGraph  ──>  DoctorDashboardScreen
    UserRole.FAMILY -> Screen.FamilyHome.route  ──>  FamilyNavGraph  ──>  FamilyDashboardScreen
    UserRole.PATIENT -> Screen.PatientHome.route ──> PatientNavGraph ──>  PatientTodayScreen
}
```

---

## 4. Root Causes: Why You See the Family Dashboard as Doctor

### Root Cause 1: Existing Google Account Role Persistence in Firestore
In [`AuthRepository.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/domain/repository/AuthRepository.kt#L55-L111):
1. When you sign in with Google as a Doctor, `AuthRepository.signInWithGoogle(idToken, selectedRole)` queries Firestore `/users/{uid}`.
2. If that Google account was previously used during testing or default sign-in, Firestore contains a document with `role: "FAMILY"`.
3. If `existingUser.profileCompleted` is false, `AuthRepository.kt` lines 100–111 copies the existing record (`existingUser.copy(...)`) **without updating `user.role` to `selectedRole` (`DOCTOR`)**.
4. It returns `user` with `role = FAMILY`.
5. [`LoginScreen.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/onboarding/LoginScreen.kt#L87-L96) passes `actualRole = user.role` (`FAMILY`) to `onLoginSuccess`.
6. [`SmritiSaathiNavigation.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/navigation/SmritiSaathiNavigation.kt#L100) routes `FAMILY` directly to `Screen.FamilyHome.route` (the Family Dashboard).

### Root Cause 2: Hardcoded Role Fallback in `AuthRepository.getCurrentUser()`
In [`AuthRepository.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/domain/repository/AuthRepository.kt#L274-L284):
```kotlin
fun getCurrentUser(): User? {
    val firebaseUser = auth.currentUser ?: return null
    return User(
        id = firebaseUser.uid,
        phoneNumber = firebaseUser.phoneNumber ?: "",
        role = UserRole.FAMILY, // HARDCODED DEFAULT!
        name = firebaseUser.displayName ?: "",
        email = firebaseUser.email,
        profilePhotoUrl = firebaseUser.photoUrl?.toString()
    )
}
```
When the app boots, [`SplashScreen.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/onboarding/SplashScreen.kt#L71-L79) checks `currentUser.role`. Because `getCurrentUser()` defaults to `UserRole.FAMILY`, any existing cached session immediately routes to `onNavigateToFamilyHome()` on splash screen dismiss.

### Root Cause 3: DataStore Legacy Session Key Collision
In [`DataStoreManager.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/data/datastore/DataStoreManager.kt#L20-L26), if a paired patient ID was previously saved on the device/emulator, [`SplashScreen.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/onboarding/SplashScreen.kt#L66) prioritizes `!pairedPatientId.isNullOrBlank() -> onNavigateToPatientHome()` ahead of user role evaluation.

---

## 5. Recommended Minimal Fix

To connect the existing Doctor Dashboard properly without redesigning or duplicating code:

1. **Update `AuthRepository.signInWithGoogle`**: When an uncompleted profile signs in with `selectedRole`, update `role = selectedRole` and generate a `doctorCode = "DOC-XXXX"` if `selectedRole == UserRole.DOCTOR`.
2. **Fix `AuthRepository.getCurrentUser()` / `AuthViewModel.checkCurrentUser()`**: Retrieve the role from Firestore or DataStore (`user_role_$uid`) instead of hardcoding `UserRole.FAMILY`.
3. **Ensure `LoginScreen.kt` and `SplashScreen.kt` preserve selected Doctor role**: Validate that the Doctor role routes strictly to `Screen.DoctorHome.route`.

---

## Summary Verdict

- **Existing Doctor Dashboard:** **PRESENT & FULLY IMPLEMENTED** (`DoctorDashboardScreen.kt`, `DoctorPatientDetailScreen.kt`, `DoctorNavGraph.kt`).
- **Issue:** Authentication role lookup / session fallback defaulting to `FAMILY`.
- **Fix:** Minimal adjustment in auth role synchronization to ensure the Doctor route activates.
