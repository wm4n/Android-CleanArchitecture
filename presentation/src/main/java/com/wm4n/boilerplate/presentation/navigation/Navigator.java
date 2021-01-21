/*
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
package com.wm4n.boilerplate.presentation.navigation;

import android.content.Context;

import androidx.annotation.NonNull;

import com.wm4n.boilerplate.presentation.feature.restaurant.RestaurantDetailsActivity;
import com.wm4n.boilerplate.presentation.feature.restaurant.RestaurantListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

  @Inject
  public Navigator() {
    //empty
  }

  /**
   * Goes to the restaurant list screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void toRestaurantList(@NonNull Context context) {
    context.startActivity(RestaurantListActivity.getLaunchIntent(context));
  }

  /**
   * Goes to the restaurant details screen.
   *
   * @param context A Context needed to open the destiny activity.
   * @param restaurantId The target restaurant to open
   */
  public void toRestaurantDetails(@NonNull Context context, @NonNull String restaurantId) {
    context.startActivity(RestaurantDetailsActivity.getLaunchIntent(context, restaurantId));
  }
}
