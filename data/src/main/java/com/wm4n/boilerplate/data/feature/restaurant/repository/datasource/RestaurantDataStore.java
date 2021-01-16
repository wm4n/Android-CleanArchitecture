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

import com.wm4n.boilerplate.data.entity.PaginationEntity;
import com.wm4n.boilerplate.data.feature.restaurant.entity.RestaurantEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface RestaurantDataStore {
  /**
   * Get an {@link Observable} which will emit a List of {@link RestaurantEntity}.
   */
  Observable<PaginationEntity<RestaurantEntity>> restaurantEntityList(int page);

  /**
   * Get an {@link Observable} which will emit a {@link RestaurantEntity} by its id.
   *
   * @param id The id to retrieve restaurant data.
   */
  Observable<RestaurantEntity> restaurantEntityDetails(final String id);
}
