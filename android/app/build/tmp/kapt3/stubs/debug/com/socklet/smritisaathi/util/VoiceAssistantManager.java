package com.socklet.smritisaathi.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import com.socklet.smritisaathi.data.datastore.DataStoreManager;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\bJ\u0006\u0010\u0013\u001a\u00020\u000eJ \u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\b2\u0010\b\u0002\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0017J\u0006\u0010\u0018\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/socklet/smritisaathi/util/VoiceAssistantManager;", "Landroid/speech/tts/TextToSpeech$OnInitListener;", "context", "Landroid/content/Context;", "dataStoreManager", "Lcom/socklet/smritisaathi/data/datastore/DataStoreManager;", "(Landroid/content/Context;Lcom/socklet/smritisaathi/data/datastore/DataStoreManager;)V", "currentLanguageCode", "", "isInitialized", "", "tts", "Landroid/speech/tts/TextToSpeech;", "onInit", "", "status", "", "setLanguageByCode", "code", "shutdown", "speak", "text", "onDone", "Lkotlin/Function0;", "stop", "app_debug"})
public final class VoiceAssistantManager implements android.speech.tts.TextToSpeech.OnInitListener {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.data.datastore.DataStoreManager dataStoreManager = null;
    @org.jetbrains.annotations.Nullable
    private android.speech.tts.TextToSpeech tts;
    private boolean isInitialized = false;
    @org.jetbrains.annotations.NotNull
    private java.lang.String currentLanguageCode = "en";
    
    @javax.inject.Inject
    public VoiceAssistantManager(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.data.datastore.DataStoreManager dataStoreManager) {
        super();
    }
    
    @java.lang.Override
    public void onInit(int status) {
    }
    
    public final void setLanguageByCode(@org.jetbrains.annotations.NotNull
    java.lang.String code) {
    }
    
    public final void speak(@org.jetbrains.annotations.NotNull
    java.lang.String text, @org.jetbrains.annotations.Nullable
    kotlin.jvm.functions.Function0<kotlin.Unit> onDone) {
    }
    
    public final void stop() {
    }
    
    public final void shutdown() {
    }
}