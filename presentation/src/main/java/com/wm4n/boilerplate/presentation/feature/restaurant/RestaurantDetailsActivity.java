/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package com.wm4n.boilerplate.presentation.feature.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.wm4n.boilerplate.data.feature.restaurant.repository.datasource.RestaurantUtil;
import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.presentation.AndroidApplication;
import com.wm4n.boilerplate.presentation.R;
import com.wm4n.boilerplate.presentation.databinding.RestaurantDetailBinding;
import com.wm4n.boilerplate.presentation.internal.di.components.DaggerUserComponent;
import com.wm4n.boilerplate.presentation.internal.di.components.UserComponent;
import com.wm4n.boilerplate.presentation.internal.di.modules.ActivityModule;
import com.wm4n.boilerplate.presentation.view.InvokeCallback;

import javax.inject.Inject;

public class RestaurantDetailsActivity extends AppCompatActivity implements
        RestaurantDetailsContract.View {

    public static Intent getLaunchIntent(Context context, String restaurantId) {
        Intent intent = new Intent(context, RestaurantDetailsActivity.class);
        intent.putExtra(KEY_RESTAURANT_ID, restaurantId );
        return intent;
    }


    public static final String KEY_RESTAURANT_ID = "key_restaurant_id";

    private RestaurantDetailBinding mViewBinding;

    @Inject
    RestaurantDetailsPresenter mPresenter;

    private UserComponent userComponent;

    private void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
            .applicationComponent(((AndroidApplication) getApplication()).getApplicationComponent())
            .activityModule(new ActivityModule(this))
            .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initializeInjector();
        super.onCreate(savedInstanceState);
        userComponent.inject(this);
        mViewBinding = RestaurantDetailBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());
        // Get restaurant ID from extras
        String restaurantId = null;
        if(getIntent().getExtras() != null) {
           restaurantId = getIntent().getExtras().getString(KEY_RESTAURANT_ID);
        }
        if (restaurantId == null) {
            throw new IllegalArgumentException("Must pass extra " + KEY_RESTAURANT_ID);
        }

        mPresenter.setRestaurant(restaurantId);
        mPresenter.setView(this);

        Looper.myQueue()
            .addIdleHandler(
                () -> {
                    if(mPresenter != null) {
                        mPresenter.viewReady();
                    }
                    return false;
                });
    }

    @Override
    public void renderContentLoading() {

    }

    @Override
    public void renderDetail(@NonNull Restaurant restaurant, @Nullable InvokeCallback onAddRating) {
        mViewBinding.restaurantName.setText(restaurant.getName());
        mViewBinding.restaurantRating.setRating((float) restaurant.getAvgRating());
        mViewBinding.restaurantNumRatings.setText(getString(R.string.fmt_num_ratings, restaurant.getNumRatings()));
        mViewBinding.restaurantCity.setText(restaurant.getCity());
        mViewBinding.restaurantCategory.setText(restaurant.getCategory());
        mViewBinding.restaurantPrice.setText(RestaurantUtil.getPriceString(restaurant));

        // Background image
        Glide.with(mViewBinding.restaurantImage.getContext())
            .load(restaurant.getPhoto())
            .into(mViewBinding.restaurantImage);
    }

    //    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.restaurant_button_back:
//                onBackArrowClicked(v);
//                break;
//            case R.id.fab_show_rating_dialog:
//                onAddRatingClicked(v);
//                break;
//        }
//    }
//
//    public void onBackArrowClicked(View view) {
//        onBackPressed();
//    }
//
//    public void onAddRatingClicked(View view) {
//    }

}
