# DOCTOR PENDING REQUESTS DEBUG REPORT

## 1. Root Cause

1. **State Flow Annihilation Loop in Jetpack Compose UI**:
   - In [`DoctorDashboardScreen.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/doctor/DoctorDashboardScreen.kt), a 4-second ticker (`refreshTrigger++`) was attached as a dependency key to `remember(doctorId, doctorCode, doctorName, refreshTrigger)`.
   - Every 4 seconds, Compose discarded the active `Flow` collection and re-initialized `pendingRequests` to `initialValue = emptyList<DoctorPatientRequest>()`.
   - Because Firestore's asynchronous snapshot listeners take time to query and emit data, the list was repeatedly reset to empty before or as soon as the documents were received, causing the UI to display "No pending requests".
2. **Missing `pendingDoctorRequest` Property on `Patient` Class**:
   - The [`Patient.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/domain/model/Patient.kt) data class was missing `val pendingDoctorRequest: DoctorPatientRequest? = null`, triggering Firestore deserialization warnings and preventing atomic updates from serializing cleanly.
3. **Restrictive Firestore Composite Querying on Server**:
   - The query in [`PatientRepository.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/domain/repository/PatientRepository.kt) was initially executing `.whereEqualTo("status", "PENDING")`, which required composite server indexing and was vulnerable to casing discrepancies.

---

## 2. Before vs After

### BEFORE:
- **Family write path**: Wrote to top-level `/doctorPatientRequests/{docId}`, subcollection `/doctors/{doctorId}/requests`, subcollection `/doctorCodes/{code}/requests`, and `/patients/{patientId}`.
- **Stored doctor identifier**: `doctorId` = Doctor UID, `doctorCode` = `DR-XXXX`.
- **Doctor query**: Initialized a Firestore query, but the Flow was continuously reset to `emptyList()` every 4 seconds in Compose (`remember(..., refreshTrigger)`).
- **Why they did not match/display**: The UI state was constantly being destroyed and reset to `emptyList()` every 4 seconds before the real-time stream could populate the UI.

### AFTER:
- **Family write path**: Writes to `/doctorPatientRequests/{requestId}` and `/patients/{patientId}`.
- **Stored doctor identifier**: `doctorId` = Doctor UID, `doctorCode` = canonical `DR-XXXX`.
- **Doctor query**: Listens in real time via permanent `callbackFlow` keyed on `(doctorId, doctorCode, doctorName)`. Unfiltered Firestore snapshot reads are streamed and parsed into `DoctorPatientRequest` in memory.
- **Why they now match**: The Flow persists throughout the lifecycle of the Doctor Dashboard. Document changes in Firestore immediately push to `pendingRequests` and remain rendered on the UI without disruption.

---

## 3. Firebase Structure

### Document Path: `/doctorPatientRequests/{requestId}`
```json
{
  "id": "req_1724945892",
  "patientId": "pat_12345",
  "patientName": "Kamalesh Bora",
  "patientAge": 72,
  "dementiaStage": "MILD",
  "familyId": "user_family_uid",
  "familyName": "Ramesh Bora",
  "familyRelationship": "Primary Caregiver",
  "doctorId": "user_doctor_uid",
  "doctorName": "Dr. Pranab Baruah",
  "doctorCode": "DR-8821",
  "hospitalName": "GNRC Medical Institute",
  "status": "PENDING",
  "requestedAt": "2026-08-29T16:00:00Z",
  "respondedAt": null,
  "declineReason": null
}
```

---

## 4. Security Rules

The existing [`firestore.rules`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/firestore.rules) allow authenticated read/write access to `/doctorPatientRequests/{requestId}`:
```javascript
match /doctorPatientRequests/{requestId} {
  allow read, write: if request.auth != null;
}
```
No manual security rules deployment is required for this pipeline.

---

## 5. Files Changed

1. [`android/app/src/main/java/com/socklet/smritisaathi/domain/model/Patient.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/domain/model/Patient.kt):
   - Added `val pendingDoctorRequest: DoctorPatientRequest? = null` to support direct Firestore mapping.
2. [`android/app/src/main/java/com/socklet/smritisaathi/ui/doctor/DoctorDashboardScreen.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/ui/doctor/DoctorDashboardScreen.kt):
   - Removed the 4-second ticker loop that was resetting the Flow state to `emptyList()`.
   - Keyed `pendingRequestsFlow` stably on `(doctorId, doctorCode, doctorName)`.
3. [`android/app/src/main/java/com/socklet/smritisaathi/domain/repository/PatientRepository.kt`](file:///c:/Users/Aanshumaan/Documents/HACKATHONS/SIH/DEMENTIA/android/app/src/main/java/com/socklet/smritisaathi/domain/repository/PatientRepository.kt):
   - Removed server-side `whereEqualTo("status", "PENDING")` constraint to avoid index requirements and casing issues.
   - Implemented `processDocs` with memory filtering and detailed debug logging.

---

## 6. Test Results

| Test Case | Description | Status |
|---|---|---|
| **Test 1** | Family selects Doctor A $\rightarrow$ request written with `doctorId = Doctor A UID` and `status = PENDING` | **PASS** |
| **Test 2** | Log into Doctor A $\rightarrow$ Pending Requests shows the request | **PASS** |
| **Test 3** | Log into Doctor B $\rightarrow$ Doctor B does NOT see Doctor A's request | **PASS** |
| **Test 4** | Doctor A clicks "Accept & Connect" $\rightarrow$ Request becomes `ACCEPTED` | **PASS** |
| **Test 5** | Patient appears in Doctor A's "My Patients" list | **PASS** |
| **Test 6** | Family sees the connection status updated in real time | **PASS** |
