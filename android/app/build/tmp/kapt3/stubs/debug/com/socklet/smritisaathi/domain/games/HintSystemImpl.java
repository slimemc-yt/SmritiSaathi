package com.socklet.smritisaathi.domain.games;

import com.socklet.smritisaathi.domain.model.GamePerformance;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0004H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\b\u00a8\u0006\u0012"}, d2 = {"Lcom/socklet/smritisaathi/domain/games/HintSystemImpl;", "Lcom/socklet/smritisaathi/domain/games/HintSystem;", "()V", "_hintsUsed", "", "_maxHints", "hintsUsed", "getHintsUsed", "()I", "maxHints", "getMaxHints", "resetHints", "", "shouldOfferHint", "", "consecutiveMistakes", "useHint", "Lcom/socklet/smritisaathi/domain/games/Hint;", "app_debug"})
public final class HintSystemImpl implements com.socklet.smritisaathi.domain.games.HintSystem {
    private int _hintsUsed = 0;
    private final int _maxHints = 3;
    
    @javax.inject.Inject
    public HintSystemImpl() {
        super();
    }
    
    @java.lang.Override
    public int getHintsUsed() {
        return 0;
    }
    
    @java.lang.Override
    public int getMaxHints() {
        return 0;
    }
    
    @java.lang.Override
    public boolean shouldOfferHint(int consecutiveMistakes) {
        return false;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.socklet.smritisaathi.domain.games.Hint useHint() {
        return null;
    }
    
    @java.lang.Override
    public void resetHints() {
    }
}