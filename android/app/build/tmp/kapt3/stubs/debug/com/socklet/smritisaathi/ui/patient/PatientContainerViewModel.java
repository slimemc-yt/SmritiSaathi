package com.socklet.smritisaathi.ui.patient;

import androidx.lifecycle.ViewModel;
import com.socklet.smritisaathi.data.api.ApiManager;
import com.socklet.smritisaathi.data.datastore.DataStoreManager;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler;
import com.socklet.smritisaathi.util.VoiceAssistantManager;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/socklet/smritisaathi/ui/patient/PatientContainerViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/socklet/smritisaathi/domain/repository/PatientRepository;", "voiceAssistant", "Lcom/socklet/smritisaathi/util/VoiceAssistantManager;", "gameScheduler", "Lcom/socklet/smritisaathi/domain/scheduler/AdaptiveGameScheduler;", "dataStoreManager", "Lcom/socklet/smritisaathi/data/datastore/DataStoreManager;", "apiManager", "Lcom/socklet/smritisaathi/data/api/ApiManager;", "(Lcom/socklet/smritisaathi/domain/repository/PatientRepository;Lcom/socklet/smritisaathi/util/VoiceAssistantManager;Lcom/socklet/smritisaathi/domain/scheduler/AdaptiveGameScheduler;Lcom/socklet/smritisaathi/data/datastore/DataStoreManager;Lcom/socklet/smritisaathi/data/api/ApiManager;)V", "getApiManager", "()Lcom/socklet/smritisaathi/data/api/ApiManager;", "getDataStoreManager", "()Lcom/socklet/smritisaathi/data/datastore/DataStoreManager;", "getGameScheduler", "()Lcom/socklet/smritisaathi/domain/scheduler/AdaptiveGameScheduler;", "getRepository", "()Lcom/socklet/smritisaathi/domain/repository/PatientRepository;", "getVoiceAssistant", "()Lcom/socklet/smritisaathi/util/VoiceAssistantManager;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class PatientContainerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.repository.PatientRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.util.VoiceAssistantManager voiceAssistant = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler gameScheduler = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.data.datastore.DataStoreManager dataStoreManager = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.data.api.ApiManager apiManager = null;
    
    @javax.inject.Inject
    public PatientContainerViewModel(@org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.repository.PatientRepository repository, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.util.VoiceAssistantManager voiceAssistant, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler gameScheduler, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.data.datastore.DataStoreManager dataStoreManager, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.data.api.ApiManager apiManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.repository.PatientRepository getRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.util.VoiceAssistantManager getVoiceAssistant() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler getGameScheduler() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.data.datastore.DataStoreManager getDataStoreManager() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.data.api.ApiManager getApiManager() {
        return null;
    }
}