package com.socklet.smritisaathi.domain.games;

import com.socklet.smritisaathi.domain.model.GamePerformance;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\u0004H\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0011"}, d2 = {"Lcom/socklet/smritisaathi/domain/games/DifficultyControllerImpl;", "Lcom/socklet/smritisaathi/domain/games/DifficultyController;", "()V", "_consecutiveSuccess", "", "_currentDifficulty", "currentDifficulty", "getCurrentDifficulty", "()I", "getRecommendedDifficulty", "resetDifficulty", "", "updateDifficulty", "accuracy", "", "mistakes", "consecutiveSuccess", "app_debug"})
public final class DifficultyControllerImpl implements com.socklet.smritisaathi.domain.games.DifficultyController {
    private int _currentDifficulty = 1;
    private int _consecutiveSuccess = 0;
    
    @javax.inject.Inject
    public DifficultyControllerImpl() {
        super();
    }
    
    @java.lang.Override
    public int getCurrentDifficulty() {
        return 0;
    }
    
    @java.lang.Override
    public void updateDifficulty(float accuracy, int mistakes, int consecutiveSuccess) {
    }
    
    @java.lang.Override
    public int getRecommendedDifficulty() {
        return 0;
    }
    
    @java.lang.Override
    public void resetDifficulty() {
    }
}