# API Integration Audit

**Last Updated:** 2026-08-29  
**Project:** SmritiSaathi - SIH Dementia Care App

---

## Overview

This document audits all external API/service integrations in the SmritiSaathi application, identifying what's implemented, what's mocked, and what requires manual setup.

---

## API Status Table

| Feature | API/Service | Existing Code | Current Status | Real API Called? | Fallback Used? | API Key/Setup Needed | Manual Step Required |
|---------|-------------|---------------|----------------|------------------|----------------|---------------------|---------------------|
| **AI Content Generation** | Google Gemini API | ✅ Partial | ⚠️ Configured but not fully integrated | ❌ No | ✅ Yes (hardcoded routines) | ✅ YES - Added to local.properties | ✅ Rebuild app |
| **Text-to-Speech** | Android Built-in TTS | ✅ Implemented | ✅ Working | ✅ Yes | ✅ Yes (language fallback) | ❌ No | ❌ No |
| **Text-to-Speech (Enhanced)** | Google Cloud TTS | ❌ Not implemented | ❌ Not implemented | ❌ No | ✅ Yes (Android TTS) | ✅ YES - Required for NE languages | ✅ Enable API, get key |
| **Translation** | None | ❌ Not implemented | ❌ Not implemented | ❌ No | ❌ No | ❌ No | ❌ No |
| **Speech-to-Text** | None | ❌ Not implemented | ❌ Not implemented | ❌ No | ❌ No | ❌ No | ❌ No |
| **Authentication** | Firebase Auth | ✅ Implemented | ✅ Working | ✅ Yes | ❌ No | ✅ YES - Already configured | ❌ No |
| **Database** | Firebase Firestore | ✅ Implemented | ✅ Working | ✅ Yes | ❌ No | ✅ YES - Already configured | ❌ No |
| **File Storage** | Firebase Storage | ✅ Implemented | ⚠️ Metadata-only mode | ❌ No (files not uploaded) | ✅ Yes (metadata only) | ✅ YES - Optional for file uploads | ✅ Enable if needed |
| **BHASHINI** | None | ❌ Not implemented | ❌ Not implemented | ❌ No | ❌ No | ❌ No | ❌ No |

---

## Detailed API Analysis

### 1. Google Gemini API (AI Content Generation)

**Status:** ⚠️ Configured but not fully integrated

**Current Implementation:**
- SDK added: `com.google.ai.client.generativeai:generativeai:0.9.0`
- ApiManager.kt created with `generateContent()` and `generateRoutine()` methods
- API key added to local.properties
- BuildConfig configured to read API key

**What's Missing:**
- VoiceAssistantManager doesn't use ApiManager yet
- Routines are still hardcoded in PatientRepository
- No UI to trigger AI-generated content
- Error handling for API failures not integrated into UI

**How to Enable:**
1. ✅ API key added to `android/local.properties`
2. ✅ Build.gradle configured to read key
3. ⚠️ Rebuild app: `./gradlew assembleDebug`
4. ⚠️ Update VoiceAssistantManager to use ApiManager
5. ⚠️ Update PatientRepository to call `generateRoutine()`

**Free Tier Limits:**
- 60 requests per minute
- 1 million tokens per minute
- 1500 requests per day
- **Sufficient for hackathon demo**

**Verification Steps:**
```bash
# After rebuild, check logs for:
adb logcat | grep "ApiManager"
# Should see: "✓ Gemini API call successful"
```

---

### 2. Text-to-Speech (Android Built-in)

**Status:** ✅ Working

**Current Implementation:**
- VoiceAssistantManager.kt uses Android's built-in TTS
- Supports: English, Hindi, Assamese (limited), Bengali
- Falls back to Hindi for unsupported languages

**Limitations:**
- North Eastern languages (Mizo, Manipuri, Khasi, Garo, Bodo, Nagamese) not well supported
- Quality varies by device
- No voice selection

**Recommendation:**
- Keep Android TTS as primary (free, no setup)
- Add Google Cloud TTS as optional enhancement for better NE language support

---

### 3. Google Cloud Text-to-Speech (Enhanced TTS)

**Status:** ❌ Not implemented

**Why Needed:**
- Better support for North Eastern Indian languages
- Higher quality voices
- More voice options

**Setup Required:**
1. Go to Google Cloud Console
2. Enable "Cloud Text-to-Speech API"
3. Create API key or service account
4. Add to local.properties: `GOOGLE_CLOUD_TTS_KEY=...`
5. Add dependency: `com.google.cloud:google-cloud-texttospeech:2.20.0`

**Free Tier:**
- 1 million characters/month (Standard voices)
- 100K characters/month (WaveNet voices)
- **Sufficient for hackathon**

**Recommendation:**
- **NOT required for hackathon** - Android TTS is sufficient
- Implement only if judges specifically ask for better NE language support

---

### 4. Firebase Storage (File Uploads)

**Status:** ⚠️ Metadata-only mode

**Current Implementation:**
- Medical reports store metadata only (filename, type, date)
- No actual file uploads to Firebase Storage
- Implemented to avoid billing requirements

**Why Metadata-Only:**
- Firebase Storage free tier requires billing setup
- Hackathon doesn't need actual file storage
- Metadata demonstrates the feature

**To Enable Real File Uploads:**
1. Add billing account to Firebase (still free up to limits)
2. Update PatientRepository.uploadMedicalReport() to actually upload
3. Update UI to show uploaded files

**Free Tier (with billing):**
- 5 GB storage
- 1 GB/day download
- 20K uploads/day
- **Sufficient for hackathon**

**Recommendation:**
- **Keep metadata-only for now** - works for demo
- Enable real uploads only if specifically requested

---

### 5. BHASHINI API

**Status:** ❌ Not implemented

**What is BHASHINI:**
- Indian government's language translation API
- Supports translation, TTS, STT for Indian languages
- Requires registration and API key

**Why Not Implemented:**
- Registration process takes 1-2 days
- Android TTS + Gemini API sufficient for hackathon
- BHASHINI TTS quality similar to Android TTS for most languages

**Recommendation:**
- **NOT required for hackathon**
- Use Android TTS + Gemini for language features
- Implement BHASHINI only if judges specifically request it

---

### 6. Firebase Auth & Firestore

**Status:** ✅ Working

**Current Implementation:**
- Google Sign-In configured
- Anonymous auth for patient devices
- Firestore security rules deployed
- User profiles, patient data, relationships all working

**No Action Required**

---

## Summary: What's Working vs What's Not

### ✅ Fully Working
- Firebase Auth (Google Sign-In, Anonymous)
- Firebase Firestore (data persistence)
- Android TTS (basic language support)
- Patient pairing and sync
- Account persistence across devices

### ⚠️ Partially Working
- Gemini API (configured but not integrated into UI)
- Firebase Storage (metadata-only, no file uploads)

### ❌ Not Implemented
- Google Cloud TTS (enhanced language support)
- BHASHINI (translation, advanced TTS)
- Speech-to-Text
- Real file uploads

---

## Manual Setup Required

### A. Gemini API (REQUIRED for AI features)

**Status:** ✅ API key added, needs rebuild

**Steps:**
1. ✅ API key added to `android/local.properties`
2. ✅ Build.gradle configured
3. **Rebuild app:**
   ```bash
   cd android
   ./gradlew clean assembleDebug
   ```
4. **Verify in logs:**
   ```bash
   adb logcat | grep "ApiManager"
   ```

**Get New API Key (if needed):**
- Go to: https://makersuite.google.com/app/apikey
- Sign in with Google account
- Click "Create API Key"
- Copy key to `android/local.properties`

---

### B. Google Cloud TTS (OPTIONAL - for better NE languages)

**Status:** ❌ Not required for hackathon

**If you want to enable:**
1. Go to: https://console.cloud.google.com/
2. Create new project or select existing
3. Enable "Cloud Text-to-Speech API"
4. Go to "Credentials" → "Create Credentials" → "API Key"
5. Add to `android/local.properties`:
   ```
   GOOGLE_CLOUD_TTS_KEY=your_key_here
   ```
6. Add dependency to `build.gradle.kts`:
   ```kotlin
   implementation("com.google.cloud:google-cloud-texttospeech:2.20.0")
   ```

**Free Tier:** 1M chars/month (sufficient for hackathon)

---

### C. Firebase Storage (OPTIONAL - for file uploads)

**Status:** ⚠️ Metadata-only (works for demo)

**If you want real file uploads:**
1. Go to: https://console.firebase.google.com/
2. Select project: dementia-6e49e
3. Go to "Billing" → "Add billing account" (still free up to limits)
4. Go to "Storage" → "Get Started"
5. Update security rules if needed

**Free Tier (with billing):** 5GB storage, 1GB/day download

---

### D. BHASHINI (OPTIONAL - not required)

**Status:** ❌ Not required for hackathon

**If you want to enable:**
1. Go to: https://bhashini.gov.in/
2. Register for API access (takes 1-2 days)
3. Get API key and user ID
4. Add to `android/local.properties`:
   ```
   BHASHINI_API_KEY=your_key_here
   BHASHINI_USER_ID=your_user_id_here
   ```

**Recommendation:** Skip for hackathon - Android TTS + Gemini sufficient

---

## Recommended Action Plan

### For Hackathon (Minimum Viable)

1. ✅ **Gemini API** - Already configured, just rebuild
2. ✅ **Android TTS** - Already working
3. ✅ **Firebase** - Already working
4. ❌ **Google Cloud TTS** - Skip (not required)
5. ❌ **BHASHINI** - Skip (not required)
6. ❌ **File uploads** - Skip (metadata-only works)

### For Production (Future)

1. ✅ Enable Google Cloud TTS for better NE language support
2. ✅ Enable Firebase Storage for real file uploads
3. ✅ Implement BHASHINI for official Indian language support
4. ✅ Add Speech-to-Text for voice input
5. ✅ Implement real translation service

---

## Next Steps

1. **Rebuild app** to enable Gemini API:
   ```bash
   cd android
   ./gradlew clean assembleDebug
   ```

2. **Test Gemini integration:**
   - Open app
   - Check logs for "ApiManager" messages
   - Verify AI features work (when integrated)

3. **Integrate Gemini into UI:**
   - Update VoiceAssistantManager to use ApiManager
   - Add AI-generated routines to PatientRepository
   - Add UI triggers for AI features

4. **Document any issues** in this file

---

## Notes

- **Never commit API keys** - local.properties is gitignored
- **Free tiers are sufficient** for hackathon demo
- **Android TTS is adequate** for most languages
- **Gemini free tier** is generous (1500 req/day)
- **Metadata-only file uploads** work for demo purposes

---

**Questions?** Contact development team.
