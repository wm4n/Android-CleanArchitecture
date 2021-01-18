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
package com.wm4n.boilerplate.presentation.internal.di.modules;

import android.content.Context;
import com.wm4n.boilerplate.data.cache.UserCache;
import com.wm4n.boilerplate.data.cache.UserCacheImpl;
import com.wm4n.boilerplate.data.executor.JobExecutor;
import com.wm4n.boilerplate.data.feature.restaurant.cache.RestaurantCache;
import com.wm4n.boilerplate.data.feature.restaurant.cache.RestaurantCacheImpl;
import com.wm4n.boilerplate.data.feature.restaurant.repository.RestaurantDataRepository;
import com.wm4n.boilerplate.data.repository.UserDataRepository;
import com.wm4n.boilerplate.domain.executor.PostExecutionThread;
import com.wm4n.boilerplate.domain.executor.ThreadExecutor;
import com.wm4n.boilerplate.domain.feature.restaurant.repository.RestaurantRepository;
import com.wm4n.boilerplate.domain.repository.UserRepository;
import com.wm4n.boilerplate.presentation.AndroidApplication;
import com.wm4n.boilerplate.presentation.UIThread;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton UserCache provideUserCache(UserCacheImpl userCache) {
    return userCache;
  }

  @Provides @Singleton UserRepository provideUserRepository(UserDataRepository userDataRepository) {
    return userDataRepository;
  }

  @Provides @Singleton
  RestaurantRepository provideRestaurantRepository(RestaurantDataRepository repository) {
    return repository;
  }

  @Provides @Singleton
  RestaurantCache provideRestaurantCache(RestaurantCacheImpl cache) {
    return cache;
  }
}
