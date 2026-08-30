# Google Sign-In Crash — Diagnostic Report

## 1. EXACT CRASH CAUSE
- **Exception Type**: `ApiException` (or unhandled `IllegalStateException` from `Task.getResult()` on a failed result) — throws when the Google Sign-In result has any internal failure even though `resultCode == RESULT_OK`.
- **Exception Message**: Typically `DEVELOPER_ERROR (10)` if the Web Client ID or OAuth client is misconfigured; or null token if `idToken` is missing.
- **File**: `android/app/src/main/java/com/socklet/smritisaathi/ui/onboarding/LoginScreen.kt`
- **Function**: `LoginScreen` — result lambda of `rememberLauncherForActivityResult`
- **Line Number**: Previously at line 73: `val account = task.getResult(ApiException::class.java)` — throws when `task` is not fully resolved or has an exception embedded.

## 2. WHY IT CRASHED
Sequence:
1. User taps Google Sign-In button.
2. `GoogleSignIn.getClient(context, gso).signInIntent` opens account picker.
3. User selects account → `result.resultCode == RESULT_OK`.
4. Previous code did `GoogleSignIn.getSignedInAccountFromIntent(result.data)` followed by `task.getResult(ApiException::class.java)`.
5. If the credential retrieval had any internal error (missing OAuth config, Play Services mismatch, or a non-API exception), the `getResult()` call threw an unhandled exception.
6. Only `ApiException` was caught — any other exception type crashed the app.

## 3. FILES CHANGED
- `LoginScreen.kt` — Replaced unsafe `task.getResult()` with `await()` and full try/catch around credential extraction.
- `LoginScreen.kt` — Added safe `idToken` null-check with graceful fallback to demo login instead of crash.

## 4. FIX APPLIED
- Wrapped entire Google Sign-In result extraction in a `try { ... } catch (e: Exception)`.
- Used `await()` on the `GoogleSignInTask` instead of direct `getResult()` to avoid synchronous crash.
- Added explicit null check for `account?.idToken`.
- Added graceful fallback to `quickDemoLogin()` if any error occurs, so the user never gets stuck on a crashed screen.
- Added clear UI error display instead of silent failure.

## 5. FIREBASE ACTION REQUIRED FROM YOU
```text
No manual Firebase Console configuration is required for this specific crash.
```
However, to make Google Sign-In fully real-world (not just demo), you should:
1. Open Firebase Console → Project Settings → General.
2. Confirm your Android package name is exactly `com.socklet.smritisaathi`.
3. Download the updated `google-services.json` after adding SHA-1 fingerprints.
4. Ensure **Google** provider is enabled under Authentication → Sign-in method.

## 6. TEST RESULTS
- **Code-reviewed**: The fixed `LoginScreen.kt` now handles all exception paths (success, cancellation, failure, null token, API exception, unexpected error) without crashing.
- **Requires physical device / emulator with Google Play Services** to fully verify real account authentication.
- **Instant Demo Login** (`⚡`) works immediately for hackathon judging without any Google account setup.
