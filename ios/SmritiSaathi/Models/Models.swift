import Foundation

// MARK: - Enums
public enum DementiaStage: String, Codable, CaseIterable {
    case mild = "MILD"
    case moderate = "MODERATE"
    case severe = "SEVERE"

    public var displayName: String {
        switch self {
        case .mild: return "Mild (Early Stage)"
        case .moderate: return "Moderate (Middle Stage)"
        case .severe: return "Severe (Advanced Stage)"
        }
    }

    public var tier: Int {
        switch self {
        case .mild: return 1
        case .moderate: return 2
        case .severe: return 3
        }
    }
}

public enum ReminderStatus: String, Codable {
    case pending = "PENDING"
    case taken = "TAKEN"
    case skipped = "SKIPPED"
    case snoozed = "SNOOZED"
}

public enum ReminderType: String, Codable {
    case medicine = "MEDICINE"
    case meal = "MEAL"
    case nap = "NAP"
    case appointment = "APPOINTMENT"
    case checkIn = "CHECK_IN"
}

// MARK: - Tier Configuration
public struct TierConfig {
    public let tier: Int
    public let hasBottomNavigation: BooleanLiteralType
    public let autoLaunchOnly: BooleanLiteralType
    public let callStyleReminderTakeover: BooleanLiteralType
    public let kioskModeSupported: BooleanLiteralType
    public let voiceFirstMode: BooleanLiteralType
    public let minTouchTargetDp: CGFloat
    public let fontSizeScale: CGFloat
    public let autoDistressDetection: BooleanLiteralType
    public let consecutiveMistakesThreshold: Int

    public static func from(stage: DementiaStage, enhancedSupportEnabled: Bool = false) -> TierConfig {
        switch stage {
        case .mild:
            return TierConfig(
                tier: 1,
                hasBottomNavigation: true,
                autoLaunchOnly: false,
                callStyleReminderTakeover: true,
                kioskModeSupported: false,
                voiceFirstMode: false,
                minTouchTargetDp: 48,
                fontSizeScale: 1.0,
                autoDistressDetection: false,
                consecutiveMistakesThreshold: 3
            )
        case .moderate:
            return TierConfig(
                tier: 2,
                hasBottomNavigation: false,
                autoLaunchOnly: false,
                callStyleReminderTakeover: true,
                kioskModeSupported: false,
                voiceFirstMode: true,
                minTouchTargetDp: 64,
                fontSizeScale: 1.15,
                autoDistressDetection: true,
                consecutiveMistakesThreshold: 3
            )
        case .severe:
            return TierConfig(
                tier: 3,
                hasBottomNavigation: false,
                autoLaunchOnly: true,
                callStyleReminderTakeover: true,
                kioskModeSupported: enhancedSupportEnabled,
                voiceFirstMode: true,
                minTouchTargetDp: 80,
                fontSizeScale: 1.3,
                autoDistressDetection: true,
                consecutiveMistakesThreshold: 2
            )
        }
    }
}

// MARK: - Core Entities
public struct Patient: Identifiable, Codable {
    public var id: String = UUID().uuidString
    public var name: String
    public var age: Int
    public var gender: String = "Other"
    public var photoUrl: String? = nil
    public var preferredLanguage: String = "as" // Default Assamese for NER
    public var state: String = "Assam"
    public var dementiaStage: DementiaStage = .mild
    public var sosNumber: String = "+919876543210"
    public var doctorId: String? = nil
    public var hospitalId: String? = nil
    public var enhancedSupportEnabled: Bool = false
    public var createdAt: Date = Date()
    public var updatedAt: Date = Date()
}

public struct GameResult: Identifiable, Codable {
    public var id: String = UUID().uuidString
    public var patientId: String
    public var gameType: String
    public var score: Int
    public var difficultyLevel: Int
    public var mistakesCount: Int = 0
    public var timestamp: Date = Date()
}

public struct Reminder: Identifiable, Codable {
    public var id: String = UUID().uuidString
    public var patientId: String
    public var title: String
    public var type: ReminderType
    public var scheduledTimeString: String
    public var status: ReminderStatus = .pending
    public var dosage: String? = nil
}

public struct BehavioralAlert: Identifiable, Codable {
    public var id: String = UUID().uuidString
    public var patientId: String
    public var type: String
    public var severity: String
    public var title: String
    public var description: String
    public var timestamp: Date = Date()
    public var acknowledged: Bool = false
}

public struct ClinicalNote: Identifiable, Codable {
    public var id: String = UUID().uuidString
    public var doctorId: String
    public var doctorName: String
    public var patientId: String
    public var note: String
    public var guidanceForFamily: String
    public var createdAt: Date = Date()
}

public struct PendingAction: Identifiable, Codable {
    public var id: String = UUID().uuidString
    public var patientId: String
    public var type: String // NUDGE, PLAY_INVITE, CALM_TRIGGER
    public var senderName: String
    public var message: String
    public var targetGame: String = "CARD_MATCHING"
    public var isProcessed: Bool = false
    public var timestamp: Date = Date()
}
