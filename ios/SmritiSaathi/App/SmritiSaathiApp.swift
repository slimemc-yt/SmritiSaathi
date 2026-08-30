import SwiftUI

@main
struct SmritiSaathiApp: App {
    @State private var selectedLanguage: String? = nil
    @State private var userRole: UserRole? = nil
    @State private var currentPatient: Patient = Patient(
        name: "Hemlata Devi",
        age: 72,
        preferredLanguage: "as",
        state: "Assam",
        dementiaStage: .mild
    )
    @State private var activeGameType: String? = nil
    @State private var isShowingDistressCalming: Bool = false

    var body: some Scene {
        WindowGroup {
            if selectedLanguage == nil {
                LanguageSelectionView { code in
                    selectedLanguage = code
                }
            } else if userRole == nil {
                RoleSelectionView { role in
                    userRole = role
                }
            } else {
                switch userRole! {
                case .patient:
                    if isShowingDistressCalming {
                        DistressCalmingView {
                            isShowingDistressCalming = false
                            activeGameType = nil
                        }
                    } else if let game = activeGameType {
                        gameView(for: game)
                    } else {
                        PatientTodayView(
                            patient: currentPatient,
                            onStartActivity: {
                                let next = AdaptiveGameScheduler.shared.decideNextGame(recentResults: [])
                                activeGameType = next.gameType
                            },
                            onTriggerSOS: {
                                VoiceAssistantManager.shared.speak("Connecting to family SOS emergency contact")
                            }
                        )
                    }

                case .family:
                    FamilyDashboardView(
                        patient: currentPatient,
                        onNavigateToPlayInvite: { },
                        onNavigateToAlerts: { },
                        onNavigateToReminders: { },
                        onNavigateToReports: { }
                    )

                case .doctor:
                    DoctorDashboardView { selected in
                        currentPatient = selected
                    }
                }
            }
        }
    }

    @ViewBuilder
    private func gameView(for gameType: String) -> some View {
        switch gameType {
        case "CARD_MATCHING":
            CardMatchingGameView(
                onComplete: { _, _ in activeGameType = nil },
                onDistressTriggered: { isShowingDistressCalming = true }
            )
        case "FACE_RECOGNITION":
            FaceRecognitionGameView(
                onComplete: { _, _ in activeGameType = nil },
                onDistressTriggered: { isShowingDistressCalming = true }
            )
        case "DAILY_ROUTINE":
            DailyRoutineGameView(
                onComplete: { _, _ in activeGameType = nil },
                onDistressTriggered: { isShowingDistressCalming = true }
            )
        case "NER_CULTURE":
            NERFamiliarImagesGameView(
                onComplete: { _, _ in activeGameType = nil },
                onDistressTriggered: { isShowingDistressCalming = true }
            )
        case "SHOPPING_BASKET":
            ShoppingBasketGameView(
                onComplete: { _, _ in activeGameType = nil },
                onDistressTriggered: { isShowingDistressCalming = true }
            )
        case "QUICK_RECALL":
            QuickRecallGameView(
                onComplete: { _, _ in activeGameType = nil },
                onDistressTriggered: { isShowingDistressCalming = true }
            )
        case "LIFE_STAGE":
            LifeStageMemoryGameView(
                onComplete: { _, _ in activeGameType = nil }
            )
        case "GUESS_VOICE":
            GuessWhosSpeakingGameView(
                onComplete: { _, _ in activeGameType = nil },
                onDistressTriggered: { isShowingDistressCalming = true }
            )
        case "MUSIC_MEMORY":
            MusicMemoryGameView(
                onComplete: { _, _ in activeGameType = nil }
            )
        default:
            CardMatchingGameView(
                onComplete: { _, _ in activeGameType = nil },
                onDistressTriggered: { isShowingDistressCalming = true }
            )
        }
    }
}
