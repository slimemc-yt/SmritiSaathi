package com.socklet.smritisaathi.data.seeder;

import com.google.firebase.firestore.FirebaseFirestore;
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
public final class DatabaseSeeder_Factory implements Factory<DatabaseSeeder> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public DatabaseSeeder_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public DatabaseSeeder get() {
    return newInstance(firestoreProvider.get());
  }

  public static DatabaseSeeder_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new DatabaseSeeder_Factory(firestoreProvider);
  }

  public static DatabaseSeeder newInstance(FirebaseFirestore firestore) {
    return new DatabaseSeeder(firestore);
  }
}
