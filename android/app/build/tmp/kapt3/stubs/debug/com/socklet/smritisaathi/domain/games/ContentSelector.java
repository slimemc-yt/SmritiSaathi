package com.socklet.smritisaathi.domain.games;

import com.socklet.smritisaathi.domain.model.GamePerformance;
import kotlinx.coroutines.flow.Flow;

/**
 * Content selector to avoid repetition
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H&J\u0015\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H&\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\u0006H&J \u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fH&\u00a8\u0006\u000e"}, d2 = {"Lcom/socklet/smritisaathi/domain/games/ContentSelector;", "T", "", "getRecentlyUsed", "", "markAsUsed", "", "content", "(Ljava/lang/Object;)V", "reset", "selectContent", "count", "", "excludeRecent", "app_debug"})
public abstract interface ContentSelector<T extends java.lang.Object> {
    
    @org.jetbrains.annotations.NotNull
    public abstract java.util.List<T> selectContent(int count, int excludeRecent);
    
    public abstract void markAsUsed(T content);
    
    @org.jetbrains.annotations.NotNull
    public abstract java.util.List<T> getRecentlyUsed();
    
    public abstract void reset();
    
    /**
     * Content selector to avoid repetition
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}