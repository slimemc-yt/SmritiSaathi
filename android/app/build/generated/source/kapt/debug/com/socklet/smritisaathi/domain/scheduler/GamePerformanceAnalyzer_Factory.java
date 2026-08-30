package com.socklet.smritisaathi.domain.scheduler;

import com.socklet.smritisaathi.data.api.ApiManager;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class GamePerformanceAnalyzer_Factory implements Factory<GamePerformanceAnalyzer> {
  private final Provider<PatientRepository> repositoryProvider;

  private final Provider<ApiManager> apiManagerProvider;

  public GamePerformanceAnalyzer_Factory(Provider<PatientRepository> repositoryProvider,
      Provider<ApiManager> apiManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.apiManagerProvider = apiManagerProvider;
  }

  @Override
  public GamePerformanceAnalyzer get() {
    return newInstance(repositoryProvider.get(), apiManagerProvider.get());
  }

  public static GamePerformanceAnalyzer_Factory create(
      Provider<PatientRepository> repositoryProvider, Provider<ApiManager> apiManagerProvider) {
    return new GamePerformanceAnalyzer_Factory(repositoryProvider, apiManagerProvider);
  }

  public static GamePerformanceAnalyzer newInstance(PatientRepository repository,
      ApiManager apiManager) {
    return new GamePerformanceAnalyzer(repository, apiManager);
  }
}
