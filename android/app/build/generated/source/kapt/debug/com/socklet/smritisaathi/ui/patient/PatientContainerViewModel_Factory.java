package com.socklet.smritisaathi.ui.patient;

import com.socklet.smritisaathi.data.api.ApiManager;
import com.socklet.smritisaathi.data.datastore.DataStoreManager;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import com.socklet.smritisaathi.domain.scheduler.AdaptiveGameScheduler;
import com.socklet.smritisaathi.util.VoiceAssistantManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class PatientContainerViewModel_Factory implements Factory<PatientContainerViewModel> {
  private final Provider<PatientRepository> repositoryProvider;

  private final Provider<VoiceAssistantManager> voiceAssistantProvider;

  private final Provider<AdaptiveGameScheduler> gameSchedulerProvider;

  private final Provider<DataStoreManager> dataStoreManagerProvider;

  private final Provider<ApiManager> apiManagerProvider;

  public PatientContainerViewModel_Factory(Provider<PatientRepository> repositoryProvider,
      Provider<VoiceAssistantManager> voiceAssistantProvider,
      Provider<AdaptiveGameScheduler> gameSchedulerProvider,
      Provider<DataStoreManager> dataStoreManagerProvider,
      Provider<ApiManager> apiManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.voiceAssistantProvider = voiceAssistantProvider;
    this.gameSchedulerProvider = gameSchedulerProvider;
    this.dataStoreManagerProvider = dataStoreManagerProvider;
    this.apiManagerProvider = apiManagerProvider;
  }

  @Override
  public PatientContainerViewModel get() {
    return newInstance(repositoryProvider.get(), voiceAssistantProvider.get(), gameSchedulerProvider.get(), dataStoreManagerProvider.get(), apiManagerProvider.get());
  }

  public static PatientContainerViewModel_Factory create(
      Provider<PatientRepository> repositoryProvider,
      Provider<VoiceAssistantManager> voiceAssistantProvider,
      Provider<AdaptiveGameScheduler> gameSchedulerProvider,
      Provider<DataStoreManager> dataStoreManagerProvider,
      Provider<ApiManager> apiManagerProvider) {
    return new PatientContainerViewModel_Factory(repositoryProvider, voiceAssistantProvider, gameSchedulerProvider, dataStoreManagerProvider, apiManagerProvider);
  }

  public static PatientContainerViewModel newInstance(PatientRepository repository,
      VoiceAssistantManager voiceAssistant, AdaptiveGameScheduler gameScheduler,
      DataStoreManager dataStoreManager, ApiManager apiManager) {
    return new PatientContainerViewModel(repository, voiceAssistant, gameScheduler, dataStoreManager, apiManager);
  }
}
