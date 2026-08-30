package com.socklet.smritisaathi.util;

import android.content.Context;
import com.socklet.smritisaathi.data.datastore.DataStoreManager;
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
public final class VoiceAssistantManager_Factory implements Factory<VoiceAssistantManager> {
  private final Provider<Context> contextProvider;

  private final Provider<DataStoreManager> dataStoreManagerProvider;

  public VoiceAssistantManager_Factory(Provider<Context> contextProvider,
      Provider<DataStoreManager> dataStoreManagerProvider) {
    this.contextProvider = contextProvider;
    this.dataStoreManagerProvider = dataStoreManagerProvider;
  }

  @Override
  public VoiceAssistantManager get() {
    return newInstance(contextProvider.get(), dataStoreManagerProvider.get());
  }

  public static VoiceAssistantManager_Factory create(Provider<Context> contextProvider,
      Provider<DataStoreManager> dataStoreManagerProvider) {
    return new VoiceAssistantManager_Factory(contextProvider, dataStoreManagerProvider);
  }

  public static VoiceAssistantManager newInstance(Context context,
      DataStoreManager dataStoreManager) {
    return new VoiceAssistantManager(context, dataStoreManager);
  }
}
