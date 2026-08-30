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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\bB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\t"}, d2 = {"Lcom/socklet/smritisaathi/ui/games/CardMatchingContent;", "", "()V", "categories", "", "Lcom/socklet/smritisaathi/ui/games/CardMatchingContent$CardCategory;", "getCategories", "()Ljava/util/List;", "CardCategory", "app_debug"})
public final class CardMatchingContent {
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<com.socklet.smritisaathi.ui.games.CardMatchingContent.CardCategory> categories = null;
    @org.jetbrains.annotations.NotNull
    public static final com.socklet.smritisaathi.ui.games.CardMatchingContent INSTANCE = null;
    
    private CardMatchingContent() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.ui.games.CardMatchingContent.CardCategory> getCategories() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u00060\u0005\u00a2\u0006\u0002\u0010\bJ\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\u001b\u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u00060\u0005H\u00c6\u0003J/\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u001a\b\u0002\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u00060\u0005H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0003H\u00d6\u0001R#\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0016"}, d2 = {"Lcom/socklet/smritisaathi/ui/games/CardMatchingContent$CardCategory;", "", "name", "", "items", "", "Lkotlin/Pair;", "Landroidx/compose/ui/graphics/vector/ImageVector;", "(Ljava/lang/String;Ljava/util/List;)V", "getItems", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class CardCategory {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<kotlin.Pair<androidx.compose.ui.graphics.vector.ImageVector, java.lang.String>> items = null;
        
        public CardCategory(@org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.util.List<kotlin.Pair<androidx.compose.ui.graphics.vector.ImageVector, java.lang.String>> items) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<kotlin.Pair<androidx.compose.ui.graphics.vector.ImageVector, java.lang.String>> getItems() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<kotlin.Pair<androidx.compose.ui.graphics.vector.ImageVector, java.lang.String>> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.socklet.smritisaathi.ui.games.CardMatchingContent.CardCategory copy(@org.jetbrains.annotations.NotNull
        java.lang.String name, @org.jetbrains.annotations.NotNull
        java.util.List<kotlin.Pair<androidx.compose.ui.graphics.vector.ImageVector, java.lang.String>> items) {
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
    }
}