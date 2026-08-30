# SmritiSaathi - iOS App (Native Swift & SwiftUI)

## Architecture Overview
SmritiSaathi for iOS is built natively with **Swift 5.9+** and **SwiftUI**, matching the Android architecture feature-for-feature:
- **Zero-guilt, Voice-First UX** with `AVSpeechSynthesizer` regional language speech synthesis.
- **Three Account Experiences**:
  - `Patient`: Single-task Today screen, pulsing incoming-call styled alerts, and distress calming circuit breakers.
  - `Family`: Real-time cognitive rolling scores, adherence rates, remote voice nudges, and "Play With Grandpa" game invites.
  - `Doctor`: NER multi-district clinical triage, longitudinal trends, and clinical notes dispatcher.
- **Adaptive Game Engine**: Houses all 9 cognitive games (Card Matching, Face Recognition, Daily Routine Ordering, NER Familiar Images, Shopping Basket, Quick Recall, Life Stage Album, Guess Who's Speaking, Music Memory).

## Project Setup & Build
1. Open the project directory in Xcode 15+.
2. Ensure minimum deployment target is set to **iOS 15.0+**.
3. Add `GoogleService-Info.plist` to the project root for live Firebase synchronisation.
4. Run on any iPhone or iPad simulator or device.
