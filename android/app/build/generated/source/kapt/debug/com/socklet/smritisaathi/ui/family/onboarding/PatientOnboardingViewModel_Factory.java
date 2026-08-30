package com.socklet.smritisaathi.ui.family.onboarding;

import com.socklet.smritisaathi.data.seeder.DatabaseSeeder;
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
public final class PatientOnboardingViewModel_Factory implements Factory<PatientOnboardingViewModel> {
  private final Provider<PatientRepository> patientRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<DatabaseSeeder> databaseSeederProvider;

  public PatientOnboardingViewModel_Factory(Provider<PatientRepository> patientRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<DatabaseSeeder> databaseSeederProvider) {
    this.patientRepositoryProvider = patientRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.databaseSeederProvider = databaseSeederProvider;
  }

  @Override
  public PatientOnboardingViewModel get() {
    return newInstance(patientRepositoryProvider.get(), authRepositoryProvider.get(), databaseSeederProvider.get());
  }

  public static PatientOnboardingViewModel_Factory create(
      Provider<PatientRepository> patientRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<DatabaseSeeder> databaseSeederProvider) {
    return new PatientOnboardingViewModel_Factory(patientRepositoryProvider, authRepositoryProvider, databaseSeederProvider);
  }

  public static PatientOnboardingViewModel newInstance(PatientRepository patientRepository,
      AuthRepository authRepository, DatabaseSeeder databaseSeeder) {
    return new PatientOnboardingViewModel(patientRepository, authRepository, databaseSeeder);
  }
}
