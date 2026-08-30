import Foundation
import AVFoundation

// MARK: - Voice Assistant Manager for iOS (AVSpeechSynthesizer + Regional Fallback)
public class VoiceAssistantManager: NSObject, ObservableObject, AVSpeechSynthesizerDelegate {
    public static let shared = VoiceAssistantManager()

    private let synthesizer = AVSpeechSynthesizer()
    @Published public var isSpeaking: Bool = false
    private var currentLanguageCode: String = "as-IN"

    override init() {
        super.init()
        synthesizer.delegate = self
    }

    public func setLanguage(code: String) {
        switch code.lowercased() {
        case "as", "bn": currentLanguageCode = "bn-IN"
        case "hi": currentLanguageCode = "hi-IN"
        case "mni", "manipuri": currentLanguageCode = "hi-IN"
        case "en": currentLanguageCode = "en-IN"
        default: currentLanguageCode = "en-IN"
        }
    }

    public func speak(_ text: String, completion: (() -> Void)? = nil) {
        guard !text.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else { return }

        let utterance = AVSpeechUtterance(string: text)
        utterance.voice = AVSpeechSynthesisVoice(language: currentLanguageCode) ?? AVSpeechSynthesisVoice(language: "en-IN")
        utterance.rate = 0.42 // Slower rate tailored for elderly dementia comprehension
        utterance.pitchMultiplier = 1.05

        synthesizer.stopSpeaking(at: .immediate)
        synthesizer.speak(utterance)
    }

    public func stop() {
        synthesizer.stopSpeaking(at: .immediate)
    }

    public func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, didFinish utterance: AVSpeechUtterance) {
        DispatchQueue.main.async {
            self.isSpeaking = false
        }
    }

    public func speechSynthesizer(_ synthesizer: AVSpeechSynthesizer, didStart utterance: AVSpeechUtterance) {
        DispatchQueue.main.async {
            self.isSpeaking = true
        }
    }
}

// MARK: - Adaptive Game Scheduler
public struct NextGameRecommendation {
    public let gameType: String
    public let difficultyLevel: Int
    public let displayName: String
    public let clinicalReason: String
}

public class AdaptiveGameScheduler {
    public static let shared = AdaptiveGameScheduler()

    private let gameTypes = [
        ("CARD_MATCHING", "Memory Card Pairs", "Focuses on visual working memory and pattern recognition."),
        ("FACE_RECOGNITION", "Family Faces", "Strengthens emotional security and facial recall with loved ones."),
        ("DAILY_ROUTINE", "Daily Routine Flow", "Reinforces temporal orientation and daily living activity sequences."),
        ("NER_CULTURE", "NER Heritage Explorer", "Nurtures regional familiarity and cultural joy through local landmarks."),
        ("SHOPPING_BASKET", "Market Basket Recall", "Practices short-term list retention and functional memory."),
        ("QUICK_RECALL", "Short Story Recall", "Exercises auditory listening and narrative comprehension."),
        ("LIFE_STAGE", "Life Journey Album", "Encourages deep autobiographical memory through past milestones."),
        ("MUSIC_MEMORY", "NER Folk Melodies", "Stimulates auditory resonance and familiar rhythmic associations.")
    ]

    public func decideNextGame(recentResults: [GameResult]) -> NextGameRecommendation {
        if recentResults.isEmpty {
            return NextGameRecommendation(
                gameType: "CARD_MATCHING",
                difficultyLevel: 1,
                displayName: "Memory Card Pairs",
                clinicalReason: "Baseline evaluation session to assess visual recall and response time."
            )
        }

        let recentAvg = recentResults.prefix(5).map { $0.score }.reduce(0, +) / max(1, min(recentResults.count, 5))
        let targetLevel = recentAvg > 85 ? 3 : (recentAvg > 60 ? 2 : 1)

        // Rotate games to ensure well-rounded cognitive stimulation
        let lastGame = recentResults.first?.gameType ?? ""
        let available = gameTypes.filter { $0.0 != lastGame }
        let selected = available.randomElement() ?? gameTypes[0]

        return NextGameRecommendation(
            gameType: selected.0,
            difficultyLevel: targetLevel,
            displayName: selected.1,
            clinicalReason: selected.2 + " (Calibrated to Level \(targetLevel) based on 5-session avg score: \(recentAvg)%)"
        )
    }
}
