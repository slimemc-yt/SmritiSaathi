package com.socklet.smritisaathi.data.api;

import android.content.Context;
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
public final class ApiManager_Factory implements Factory<ApiManager> {
  private final Provider<Context> contextProvider;

  public ApiManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ApiManager get() {
    return newInstance(contextProvider.get());
  }

  public static ApiManager_Factory create(Provider<Context> contextProvider) {
    return new ApiManager_Factory(contextProvider);
  }

  public static ApiManager newInstance(Context context) {
    return new ApiManager(context);
  }
}
