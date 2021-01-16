/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wm4n.boilerplate.domain.feature.restaurant.interactor;

import com.wm4n.boilerplate.domain.UseCase;
import com.wm4n.boilerplate.domain.executor.PostExecutionThread;
import com.wm4n.boilerplate.domain.executor.ThreadExecutor;
import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.domain.feature.restaurant.repository.RestaurantRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link Restaurant}.
 */
public class GetRestaurantDetails extends UseCase<Restaurant, GetRestaurantDetails.Params> {

  private final RestaurantRepository repository;

  @Inject
  GetRestaurantDetails(RestaurantRepository repository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }

  @Override
  protected Observable<Restaurant> buildUseCaseObservable(Params params) {
    checkNotNull(params);
    return this.repository.getRestaurant(params.id);
  }

  public static final class Params {

    private final String id;

    private Params(String id) {
      this.id = id;
    }

    public static Params forRestaurant(String id) {
      return new Params(id);
    }
  }
}
