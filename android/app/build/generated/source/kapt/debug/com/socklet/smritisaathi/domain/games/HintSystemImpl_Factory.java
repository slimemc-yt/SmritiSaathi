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
public final class HintSystemImpl_Factory implements Factory<HintSystemImpl> {
  @Override
  public HintSystemImpl get() {
    return newInstance();
  }

  public static HintSystemImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static HintSystemImpl newInstance() {
    return new HintSystemImpl();
  }

  private static final class InstanceHolder {
    private static final HintSystemImpl_Factory INSTANCE = new HintSystemImpl_Factory();
  }
}
