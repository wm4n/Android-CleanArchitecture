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
package com.wm4n.boilerplate.domain.feature.restaurant.repository;

import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.domain.model.PaginationList;

import io.reactivex.Observable;

/**
 * Interface that represents a Repository for getting {@link Restaurant} related data.
 */
public interface RestaurantRepository {
  /**
   * Get an {@link Observable} which will emit a List of {@link Restaurant}.
   */
  Observable<PaginationList<Restaurant>> getRestaurantList(int page);

  /**
   * Get an {@link Observable} which will emit a {@link Restaurant}.
   *
   * @param id The user id used to retrieve restaurant data.
   */
  Observable<Restaurant> getRestaurant(String id);
}
