package com.socklet.smritisaathi.ui.games;

import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.socklet.smritisaathi.domain.model.GamePerformance;
import com.socklet.smritisaathi.domain.model.GameResult;
import com.socklet.smritisaathi.domain.model.GameType;
import com.socklet.smritisaathi.ui.theme.Dimensions;
import com.socklet.smritisaathi.util.VoiceAssistantManager;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001aH\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a&\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u00a8\u0006\u0012"}, d2 = {"CardMatchingGameScreen", "", "difficultyLevel", "", "voiceAssistant", "Lcom/socklet/smritisaathi/util/VoiceAssistantManager;", "onComplete", "Lkotlin/Function1;", "Lcom/socklet/smritisaathi/domain/model/GameResult;", "onDistressTriggered", "Lkotlin/Function0;", "onExit", "MemoryCardView", "card", "Lcom/socklet/smritisaathi/ui/games/MemoryCard;", "isHinted", "", "onClick", "app_debug"})
public final class CardMatchingGameScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void CardMatchingGameScreen(int difficultyLevel, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.util.VoiceAssistantManager voiceAssistant, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.socklet.smritisaathi.domain.model.GameResult, kotlin.Unit> onComplete, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDistressTriggered, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onExit) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void MemoryCardView(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.ui.games.MemoryCard card, boolean isHinted, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}