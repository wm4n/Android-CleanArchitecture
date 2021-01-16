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
import com.wm4n.boilerplate.domain.model.PaginationList;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Restaurant}.
 */
public class GetRestaurantList extends UseCase<PaginationList<Restaurant>, GetRestaurantList.Params> {

  private final RestaurantRepository repository;

  @Inject
  GetRestaurantList(RestaurantRepository repository, ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
  }

  @Override
  protected Observable<PaginationList<Restaurant>> buildUseCaseObservable(GetRestaurantList.Params params) {
    return this.repository.getRestaurantList(params.page);
  }

  public static final class Params {

    private int page;

    private Params(int page) {
      this.page = page;
    }

    public static Params forPage(int page) {
      return new Params(page);
    }
  }
}
