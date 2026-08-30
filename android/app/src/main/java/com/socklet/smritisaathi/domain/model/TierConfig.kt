package com.socklet.smritisaathi.domain.model

data class TierConfig(
    val tier: Int = 1, // 1 (Mild), 2 (Moderate), 3 (Severe)
    val hasBottomNavigation: Boolean = true,
    val autoLaunchOnly: Boolean = false,
    val callStyleReminderTakeover: Boolean = true,
    val kioskModeSupported: Boolean = false,
    val voiceFirstMode: Boolean = false,
    val minTouchTargetDp: Int = 48,
    val fontSizeScale: Float = 1.0f,
    val autoDistressDetection: Boolean = false,
    val consecutiveMistakesThreshold: Int = 3
) {
    companion object {
        fun fromDementiaStage(stage: DementiaStage, enhancedSupportEnabled: Boolean = false): TierConfig {
            return when (stage) {
                DementiaStage.MILD -> TierConfig(
                    tier = 1,
                    hasBottomNavigation = true,
                    autoLaunchOnly = false,
                    callStyleReminderTakeover = false,
                    kioskModeSupported = false,
                    voiceFirstMode = false,
                    minTouchTargetDp = 48,
                    fontSizeScale = 1.0f,
                    autoDistressDetection = false,
                    consecutiveMistakesThreshold = 4
                )
                DementiaStage.MODERATE -> TierConfig(
                    tier = 2,
                    hasBottomNavigation = true, // HACKATHON: Show all features for demo
                    autoLaunchOnly = false,
                    callStyleReminderTakeover = true,
                    kioskModeSupported = false,
                    voiceFirstMode = true,
                    minTouchTargetDp = 64,
                    fontSizeScale = 1.2f,
                    autoDistressDetection = true,
                    consecutiveMistakesThreshold = 3
                )
                DementiaStage.SEVERE -> TierConfig(
                    tier = 3,
                    hasBottomNavigation = true, // HACKATHON: Show all features for demo
                    autoLaunchOnly = true,
                    callStyleReminderTakeover = true,
                    kioskModeSupported = enhancedSupportEnabled,
                    voiceFirstMode = true,
                    minTouchTargetDp = 80,
                    fontSizeScale = 1.4f,
                    autoDistressDetection = true,
                    consecutiveMistakesThreshold = 2
                )
            }
        }
    }
}
