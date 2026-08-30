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
public final class ContentSelectorImpl_Factory<T> implements Factory<ContentSelectorImpl<T>> {
  @Override
  public ContentSelectorImpl<T> get() {
    return newInstance();
  }

  @SuppressWarnings("unchecked")
  public static <T> ContentSelectorImpl_Factory<T> create() {
    return InstanceHolder.INSTANCE;
  }

  public static <T> ContentSelectorImpl<T> newInstance() {
    return new ContentSelectorImpl<T>();
  }

  private static final class InstanceHolder {
    @SuppressWarnings("rawtypes")
    private static final ContentSelectorImpl_Factory INSTANCE = new ContentSelectorImpl_Factory();
  }
}
