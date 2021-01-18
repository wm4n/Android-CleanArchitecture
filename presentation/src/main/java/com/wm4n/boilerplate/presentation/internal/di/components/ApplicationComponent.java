/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wm4n.boilerplate.presentation.internal.di.components;

import android.content.Context;
import com.wm4n.boilerplate.domain.executor.PostExecutionThread;
import com.wm4n.boilerplate.domain.executor.ThreadExecutor;
import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.domain.feature.restaurant.repository.RestaurantRepository;
import com.wm4n.boilerplate.domain.repository.UserRepository;
import com.wm4n.boilerplate.presentation.feature.restaurant.RestaurantListActivity;
import com.wm4n.boilerplate.presentation.internal.di.modules.ApplicationModule;
import com.wm4n.boilerplate.presentation.view.activity.BaseActivity;

import dagger.Component;
import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  //void inject(RestaurantListActivity activity);

  //Exposed to sub-graphs.
  Context context();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();
  UserRepository userRepository();
  RestaurantRepository restaurantRepository();
}