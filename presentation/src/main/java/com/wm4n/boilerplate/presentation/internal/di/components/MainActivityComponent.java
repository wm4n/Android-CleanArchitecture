package com.wm4n.boilerplate.presentation.internal.di.components;

import com.wm4n.boilerplate.presentation.feature.restaurant.RestaurantDetailsActivity;
import com.wm4n.boilerplate.presentation.feature.restaurant.RestaurantListActivity;
import com.wm4n.boilerplate.presentation.internal.di.PerActivity;
import com.wm4n.boilerplate.presentation.internal.di.modules.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface MainActivityComponent extends ActivityComponent {
  void inject(RestaurantListActivity activity);
  void inject(RestaurantDetailsActivity activity);
}
