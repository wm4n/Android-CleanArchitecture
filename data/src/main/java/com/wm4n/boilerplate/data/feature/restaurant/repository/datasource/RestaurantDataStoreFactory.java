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
package com.wm4n.boilerplate.data.feature.restaurant.repository.datasource;

import android.content.Context;

import androidx.annotation.NonNull;

import com.wm4n.boilerplate.data.entity.mapper.UserEntityJsonMapper;
import com.wm4n.boilerplate.data.feature.restaurant.cache.RestaurantCache;
import com.wm4n.boilerplate.data.net.RestApi;
import com.wm4n.boilerplate.data.net.RestApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link RestaurantDataStore}.
 */
@Singleton
public class RestaurantDataStoreFactory {

  private final Context context;
  private final RestaurantCache cache;

  @Inject
  RestaurantDataStoreFactory(@NonNull Context context, @NonNull RestaurantCache cache) {
    this.context = context.getApplicationContext();
    this.cache = cache;
  }

  /**
   * Create {@link RestaurantDataStore} from a restaurant id.
   */
  public RestaurantDataStore create(String id) {
    RestaurantDataStore restaurantDataStore;

    if (!this.cache.isExpired() && this.cache.isCached(id)) {
      restaurantDataStore = new CacheRestaurantDataStore(cache);
    } else {
      restaurantDataStore = createRandomDataStore();
    }

    return restaurantDataStore;
  }

  /**
   * Create {@link RestaurantDataStore} to generated random data.
   */
  public RestaurantDataStore createRandomDataStore() {
    return new RandomRestaurantDataStore(context);
  }
}
