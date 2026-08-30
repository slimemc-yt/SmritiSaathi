package com.socklet.smritisaathi.domain.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
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
public final class PatientRepository_Factory implements Factory<PatientRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<FirebaseStorage> storageProvider;

  public PatientRepository_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseStorage> storageProvider) {
    this.firestoreProvider = firestoreProvider;
    this.storageProvider = storageProvider;
  }

  @Override
  public PatientRepository get() {
    return newInstance(firestoreProvider.get(), storageProvider.get());
  }

  public static PatientRepository_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseStorage> storageProvider) {
    return new PatientRepository_Factory(firestoreProvider, storageProvider);
  }

  public static PatientRepository newInstance(FirebaseFirestore firestore,
      FirebaseStorage storage) {
    return new PatientRepository(firestore, storage);
  }
}
