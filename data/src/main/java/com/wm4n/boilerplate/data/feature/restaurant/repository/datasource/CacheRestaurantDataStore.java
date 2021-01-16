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

import com.wm4n.boilerplate.data.cache.UserCache;
import com.wm4n.boilerplate.data.entity.PaginationEntity;
import com.wm4n.boilerplate.data.feature.restaurant.cache.RestaurantCache;
import com.wm4n.boilerplate.data.feature.restaurant.entity.RestaurantEntity;

import io.reactivex.Observable;

/**
 * {@link RestaurantDataStore} implementation based on file system data store.
 */
class CacheRestaurantDataStore implements RestaurantDataStore {

  private final RestaurantCache cache;

  /**
   * Construct a {@link RestaurantDataStore} based file system data store.
   *
   * @param cache A {@link UserCache} to cache data retrieved from the other source.
   */
  CacheRestaurantDataStore(RestaurantCache cache) {
    this.cache = cache;
  }

  @Override public Observable<PaginationEntity<RestaurantEntity>> restaurantEntityList(int page) {
    //TODO: implement simple cache for storing/retrieving collections of restaurant.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }

  @Override public Observable<RestaurantEntity> restaurantEntityDetails(final String id) {
     return this.cache.get(id);
  }
}
