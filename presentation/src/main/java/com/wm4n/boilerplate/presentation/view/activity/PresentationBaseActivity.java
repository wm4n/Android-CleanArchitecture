package com.wm4n.boilerplate.presentation.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.wm4n.boilerplate.presentation.AndroidApplication;
import com.wm4n.boilerplate.presentation.internal.di.HasComponent;
import com.wm4n.boilerplate.presentation.internal.di.components.ApplicationComponent;
import com.wm4n.boilerplate.presentation.internal.di.components.DaggerMainActivityComponent;
import com.wm4n.boilerplate.presentation.internal.di.components.MainActivityComponent;
import com.wm4n.boilerplate.presentation.internal.di.modules.ActivityModule;
import com.wm4n.boilerplate.presentation.navigation.Navigator;

import javax.inject.Inject;

/** Extending {@link BaseActivity} and providing injection function */
@SuppressWarnings("unused")
public abstract class PresentationBaseActivity extends BaseActivity
    implements HasComponent<MainActivityComponent> {

  @Inject
  protected Navigator navigator;

  protected MainActivityComponent mMainActivityComponent;

  private void initializeInjector() {
    this.mMainActivityComponent = DaggerMainActivityComponent.builder()
        .applicationComponent(((AndroidApplication) getApplication()).getApplicationComponent())
        .activityModule(new ActivityModule(this))
        .build();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    this.initializeInjector();
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
  }

  @Override
  public MainActivityComponent getComponent() {
    return mMainActivityComponent;
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication) getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  /**
   * Get the app's navigator
   *
   * @return app navigator
   */
  protected Navigator getNavigator() {
    return navigator;
  }
}
