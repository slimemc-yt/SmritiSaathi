package com.socklet.smritisaathi.domain.scheduler;

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
public final class MedicationAlarmScheduler_Factory implements Factory<MedicationAlarmScheduler> {
  private final Provider<Context> contextProvider;

  public MedicationAlarmScheduler_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public MedicationAlarmScheduler get() {
    return newInstance(contextProvider.get());
  }

  public static MedicationAlarmScheduler_Factory create(Provider<Context> contextProvider) {
    return new MedicationAlarmScheduler_Factory(contextProvider);
  }

  public static MedicationAlarmScheduler newInstance(Context context) {
    return new MedicationAlarmScheduler(context);
  }
}
