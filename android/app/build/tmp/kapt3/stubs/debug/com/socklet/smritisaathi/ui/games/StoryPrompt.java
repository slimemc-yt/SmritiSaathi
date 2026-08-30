package com.socklet.smritisaathi.ui.games;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import com.socklet.smritisaathi.domain.model.GameResult;
import com.socklet.smritisaathi.domain.model.GameType;
import com.socklet.smritisaathi.ui.theme.Dimensions;
import com.socklet.smritisaathi.util.VoiceAssistantManager;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J7\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000b\u00a8\u0006\u001b"}, d2 = {"Lcom/socklet/smritisaathi/ui/games/StoryPrompt;", "", "id", "", "story", "questions", "", "Lcom/socklet/smritisaathi/ui/games/RecallQuestion;", "category", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;)V", "getCategory", "()Ljava/lang/String;", "getId", "getQuestions", "()Ljava/util/List;", "getStory", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class StoryPrompt {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String story = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.ui.games.RecallQuestion> questions = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String category = null;
    
    public StoryPrompt(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String story, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.ui.games.RecallQuestion> questions, @org.jetbrains.annotations.NotNull
    java.lang.String category) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getStory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.ui.games.RecallQuestion> getQuestions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.ui.games.RecallQuestion> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.ui.games.StoryPrompt copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String story, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.ui.games.RecallQuestion> questions, @org.jetbrains.annotations.NotNull
    java.lang.String category) {
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