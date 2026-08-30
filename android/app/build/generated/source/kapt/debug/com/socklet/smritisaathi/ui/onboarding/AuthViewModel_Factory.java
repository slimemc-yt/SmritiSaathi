package com.socklet.smritisaathi.ui.onboarding;

import com.socklet.smritisaathi.data.datastore.DataStoreManager;
import com.socklet.smritisaathi.domain.repository.AuthRepository;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<PatientRepository> patientRepositoryProvider;

  private final Provider<DataStoreManager> dataStoreManagerProvider;

  public AuthViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<PatientRepository> patientRepositoryProvider,
      Provider<DataStoreManager> dataStoreManagerProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.patientRepositoryProvider = patientRepositoryProvider;
    this.dataStoreManagerProvider = dataStoreManagerProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(authRepositoryProvider.get(), patientRepositoryProvider.get(), dataStoreManagerProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<PatientRepository> patientRepositoryProvider,
      Provider<DataStoreManager> dataStoreManagerProvider) {
    return new AuthViewModel_Factory(authRepositoryProvider, patientRepositoryProvider, dataStoreManagerProvider);
  }

  public static AuthViewModel newInstance(AuthRepository authRepository,
      PatientRepository patientRepository, DataStoreManager dataStoreManager) {
    return new AuthViewModel(authRepository, patientRepository, dataStoreManager);
  }
}
