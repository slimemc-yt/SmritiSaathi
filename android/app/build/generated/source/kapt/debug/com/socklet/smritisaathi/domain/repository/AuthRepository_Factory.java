package com.socklet.smritisaathi.domain.repository;

import android.content.Context;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<Context> appContextProvider;

  private final Provider<FirebaseAuth> authProvider;

  private final Provider<FirebaseFirestore> firestoreProvider;

  public AuthRepository_Factory(Provider<Context> appContextProvider,
      Provider<FirebaseAuth> authProvider, Provider<FirebaseFirestore> firestoreProvider) {
    this.appContextProvider = appContextProvider;
    this.authProvider = authProvider;
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(appContextProvider.get(), authProvider.get(), firestoreProvider.get());
  }

  public static AuthRepository_Factory create(Provider<Context> appContextProvider,
      Provider<FirebaseAuth> authProvider, Provider<FirebaseFirestore> firestoreProvider) {
    return new AuthRepository_Factory(appContextProvider, authProvider, firestoreProvider);
  }

  public static AuthRepository newInstance(Context appContext, FirebaseAuth auth,
      FirebaseFirestore firestore) {
    return new AuthRepository(appContext, auth, firestore);
  }
}
