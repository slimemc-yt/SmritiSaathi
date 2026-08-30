package com.socklet.smritisaathi.domain.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u001f\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 -2\u00020\u0001:\u0001-Bi\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010 \u001a\u00020\u0005H\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\fH\u00c6\u0003J\t\u0010&\u001a\u00020\u0005H\u00c6\u0003Jm\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010(\u001a\u00020\u00052\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020\u0003H\u00d6\u0001J\t\u0010+\u001a\u00020,H\u00d6\u0001R\u0011\u0010\r\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011\u00a8\u0006."}, d2 = {"Lcom/socklet/smritisaathi/domain/model/TierConfig;", "", "tier", "", "hasBottomNavigation", "", "autoLaunchOnly", "callStyleReminderTakeover", "kioskModeSupported", "voiceFirstMode", "minTouchTargetDp", "fontSizeScale", "", "autoDistressDetection", "consecutiveMistakesThreshold", "(IZZZZZIFZI)V", "getAutoDistressDetection", "()Z", "getAutoLaunchOnly", "getCallStyleReminderTakeover", "getConsecutiveMistakesThreshold", "()I", "getFontSizeScale", "()F", "getHasBottomNavigation", "getKioskModeSupported", "getMinTouchTargetDp", "getTier", "getVoiceFirstMode", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "", "Companion", "app_debug"})
public final class TierConfig {
    private final int tier = 0;
    private final boolean hasBottomNavigation = false;
    private final boolean autoLaunchOnly = false;
    private final boolean callStyleReminderTakeover = false;
    private final boolean kioskModeSupported = false;
    private final boolean voiceFirstMode = false;
    private final int minTouchTargetDp = 0;
    private final float fontSizeScale = 0.0F;
    private final boolean autoDistressDetection = false;
    private final int consecutiveMistakesThreshold = 0;
    @org.jetbrains.annotations.NotNull
    public static final com.socklet.smritisaathi.domain.model.TierConfig.Companion Companion = null;
    
    public TierConfig(int tier, boolean hasBottomNavigation, boolean autoLaunchOnly, boolean callStyleReminderTakeover, boolean kioskModeSupported, boolean voiceFirstMode, int minTouchTargetDp, float fontSizeScale, boolean autoDistressDetection, int consecutiveMistakesThreshold) {
        super();
    }
    
    public final int getTier() {
        return 0;
    }
    
    public final boolean getHasBottomNavigation() {
        return false;
    }
    
    public final boolean getAutoLaunchOnly() {
        return false;
    }
    
    public final boolean getCallStyleReminderTakeover() {
        return false;
    }
    
    public final boolean getKioskModeSupported() {
        return false;
    }
    
    public final boolean getVoiceFirstMode() {
        return false;
    }
    
    public final int getMinTouchTargetDp() {
        return 0;
    }
    
    public final float getFontSizeScale() {
        return 0.0F;
    }
    
    public final boolean getAutoDistressDetection() {
        return false;
    }
    
    public final int getConsecutiveMistakesThreshold() {
        return 0;
    }
    
    public TierConfig() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final float component8() {
        return 0.0F;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.TierConfig copy(int tier, boolean hasBottomNavigation, boolean autoLaunchOnly, boolean callStyleReminderTakeover, boolean kioskModeSupported, boolean voiceFirstMode, int minTouchTargetDp, float fontSizeScale, boolean autoDistressDetection, int consecutiveMistakesThreshold) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a8\u0006\t"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/TierConfig$Companion;", "", "()V", "fromDementiaStage", "Lcom/socklet/smritisaathi/domain/model/TierConfig;", "stage", "Lcom/socklet/smritisaathi/domain/model/DementiaStage;", "enhancedSupportEnabled", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.socklet.smritisaathi.domain.model.TierConfig fromDementiaStage(@org.jetbrains.annotations.NotNull
        com.socklet.smritisaathi.domain.model.DementiaStage stage, boolean enhancedSupportEnabled) {
            return null;
        }
    }
}