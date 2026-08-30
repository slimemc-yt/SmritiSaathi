package com.socklet.smritisaathi.domain.games;

import com.socklet.smritisaathi.domain.model.GamePerformance;
import kotlinx.coroutines.flow.Flow;

/**
 * Hint system for gentle assistance
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\b\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H&J\n\u0010\r\u001a\u0004\u0018\u00010\u000eH&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\u00a8\u0006\u000f"}, d2 = {"Lcom/socklet/smritisaathi/domain/games/HintSystem;", "", "hintsUsed", "", "getHintsUsed", "()I", "maxHints", "getMaxHints", "resetHints", "", "shouldOfferHint", "", "consecutiveMistakes", "useHint", "Lcom/socklet/smritisaathi/domain/games/Hint;", "app_debug"})
public abstract interface HintSystem {
    
    public abstract int getHintsUsed();
    
    public abstract int getMaxHints();
    
    public abstract boolean shouldOfferHint(int consecutiveMistakes);
    
    @org.jetbrains.annotations.Nullable
    public abstract com.socklet.smritisaathi.domain.games.Hint useHint();
    
    public abstract void resetHints();
}