package com.socklet.smritisaathi.domain.games;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class DifficultyControllerImpl_Factory implements Factory<DifficultyControllerImpl> {
  @Override
  public DifficultyControllerImpl get() {
    return newInstance();
  }

  public static DifficultyControllerImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DifficultyControllerImpl newInstance() {
    return new DifficultyControllerImpl();
  }

  private static final class InstanceHolder {
    private static final DifficultyControllerImpl_Factory INSTANCE = new DifficultyControllerImpl_Factory();
  }
}
