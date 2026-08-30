package com.socklet.smritisaathi.domain.games;

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
public final class PerformanceTrackerImpl_Factory implements Factory<PerformanceTrackerImpl> {
  private final Provider<PatientRepository> repositoryProvider;

  public PerformanceTrackerImpl_Factory(Provider<PatientRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PerformanceTrackerImpl get() {
    return newInstance(repositoryProvider.get());
  }

  public static PerformanceTrackerImpl_Factory create(
      Provider<PatientRepository> repositoryProvider) {
    return new PerformanceTrackerImpl_Factory(repositoryProvider);
  }

  public static PerformanceTrackerImpl newInstance(PatientRepository repository) {
    return new PerformanceTrackerImpl(repository);
  }
}
