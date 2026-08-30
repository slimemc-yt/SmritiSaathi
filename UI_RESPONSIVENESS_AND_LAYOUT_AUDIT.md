# SmritiSaathi - UI Responsiveness, Overflow & Layout Audit

## 1. Root Causes Found

| Issue | Root Cause in Code | Global Fix Strategy |
| :--- | :--- | :--- |
| **Top Bar / Back Button colliding with status bar / camera notch** | `MainActivity.kt` called `enableEdgeToEdge()` without applying window insets or `safeDrawingPadding()`. | Added `Modifier.safeDrawingPadding()` and `WindowInsets.safeDrawing` to main container and Scaffolds. |
| **Bottom buttons cut off behind Android navigation gesture bar** | Fixed heights without navigation bar insets. | Handled navigation insets on `PrimaryButton` and bottom action rows. |
| **Form fields hidden behind soft keyboard** | Missing `imePadding()` and `verticalScroll(rememberScrollState())` on registration forms. | Wrapped all forms (`LoginScreen`, `PatientBasicDetailsScreen`, `DailyRoutineScreen`, `PatientPairingScreen`) in scrollable columns with `imePadding()`. |
| **Text clipping on small screens / long translations** | Hardcoded English widths, missing text wrapping, or fixed container heights. | Replaced fixed widths with `fillMaxWidth()`, enabled text wrapping with `wrapContentHeight()`, and removed arbitrary `maxLines = 1` on descriptions. |
| **Elderly touch target sizing** | Some small icon buttons were under 48dp. | Enforced `Dimensions.MinTouchTarget = 48.dp` and 56dp+ height for primary interactive elements. |

---

## 2. Screens Audited & Responsive Improvements

| Screen | Issues Found | Fix Applied | Accessibility / Responsive Status |
| :--- | :--- | :--- | :--- |
| **Splash Screen** | Fixed logo height on small screens | Dynamic weight + `wrapContentSize()` | ✅ Responsive across 4.7" to 10" |
| **Language Selection** | Language cards were fixed height; speaker button clipped | Scrollable list with flexible height cards + DataStore persistence | ✅ Responsive & multilingual |
| **Role Selection** | Fixed space between role buttons clipped on small screens | `verticalScroll` + weighted button distribution + Demo Mode action | ✅ Accessible touch targets (64dp) |
| **Login Screen** | Google button / Phone inputs hidden when keyboard opened | Added `imePadding()` + `verticalScroll` + clean error visibility | ✅ Fits with soft keyboard open |
| **Patient Pairing** | 6-Digit input cut off on narrow screens | Responsive font sizing (`letterSpacing = 4.sp`, flexible text field) | ✅ High contrast & readable |
| **Patient Onboarding (8 Steps)** | Long forms with photo pickers clipped at bottom | Each sub-screen given `verticalScroll(rememberScrollState())` + `imePadding()` | ✅ Tested with keyboard |
| **Patient "Today" Screen** | Large greeting text overlapped SOS button on small phones | Responsive layout: Top bar -> Centered Task Target -> Bottom SOS with padding | ✅ Elderly dementia-friendly (72dp button) |
| **Card Matching Game** | Cards overflowed on narrow phones (320dp width) | `LazyVerticalGrid(columns = GridCells.Fixed(2))` with adaptive aspect ratio | ✅ Tested across aspect ratios |
| **Daily Routine Game** | Step arrows clipped when activity names wrapped | Flexible `Row` with `Modifier.weight(1f)` for text + compact arrow targets | ✅ Responsive text wrapping |
| **Family Dashboard** | Stats cards overflowed horizontally on narrow phones | `Row` with `Modifier.weight(1f)` per stat card + scrollable feed | ✅ Clean spacing & wrapping |
| **Doctor Dashboard** | Patient triage rows clipped risk badges on narrow screens | Multi-line `Row` with badge wrapping and flexible search bar | ✅ Multi-patient triage list |
| **Medication Prompt Overlay** | 3 buttons (Taken / Later / Help) collided on small screens | Vertical button column with high contrast (Green / Orange / Red) | ✅ 56dp large touch targets |

---

## 3. Shared Components Fixed

1. **`PrimaryButton.kt`**: Made height adaptive (min 56dp), enabled multi-line text wrapping for long translations, and added loading spinner state.
2. **`RoleButton.kt`**: Replaced fixed height with flexible column container so role descriptions never get truncated in regional languages.
3. **`LanguageButton.kt`**: Added speaker playback icon with accessible touch target (48dp).
4. **`Dimensions.kt`**: Standardized spacing tokens (`Space4` to `Space48`), touch targets (`MinTouchTarget = 48.dp`), and responsive padding (`ScreenPadding = 16.dp` to `24.dp`).
