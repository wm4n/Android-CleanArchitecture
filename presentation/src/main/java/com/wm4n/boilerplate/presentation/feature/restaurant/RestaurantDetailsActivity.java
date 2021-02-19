package com.wm4n.boilerplate.presentation.feature.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.wm4n.boilerplate.app_core.view.InvokeCallback;
import com.wm4n.boilerplate.data.feature.restaurant.repository.datasource.RestaurantUtil;
import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.presentation.R;
import com.wm4n.boilerplate.presentation.databinding.RestaurantDetailBinding;
import com.wm4n.boilerplate.presentation.view.activity.PresentationBaseActivity;

import javax.inject.Inject;

public class RestaurantDetailsActivity extends PresentationBaseActivity implements
    RestaurantDetailsContract.View {

  public static Intent getLaunchIntent(Context context, String restaurantId) {
    Intent intent = new Intent(context, RestaurantDetailsActivity.class);
    intent.putExtra(KEY_RESTAURANT_ID, restaurantId);
    return intent;
  }

  public static final String KEY_RESTAURANT_ID = "key_restaurant_id";

  private RestaurantDetailBinding mViewBinding;
  private InvokeCallback mOnAddRating;

  @Inject
  RestaurantDetailsPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent().inject(this);

    mViewBinding = RestaurantDetailBinding.inflate(getLayoutInflater());
    setContentView(mViewBinding.getRoot());

    mViewBinding.restaurantButtonBack.setOnClickListener(v -> closeView(VIEW_OK));
    // Get restaurant ID from extras
    String restaurantId = null;
    if (getIntent().getExtras() != null) {
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
              if (mPresenter != null) {
                mPresenter.viewReady();
              }
              return false;
            });
  }

  @Override
  protected void onDestroy() {
    mPresenter.viewDestroy();
    mPresenter.cleanup();
    super.onDestroy();
  }

  @Override
  public void renderDetail(@NonNull Restaurant restaurant, @Nullable InvokeCallback onAddRating) {
    mOnAddRating = onAddRating;
    mViewBinding.restaurantName.setText(restaurant.getName());
    mViewBinding.restaurantRating.setRating((float) restaurant.getAvgRating());
    mViewBinding.restaurantNumRatings.setText(getString(R.string.fmt_num_ratings, restaurant.getNumRatings()));
    mViewBinding.restaurantCity.setText(restaurant.getCity());
    mViewBinding.restaurantCategory.setText(restaurant.getCategory());
    mViewBinding.restaurantPrice.setText(RestaurantUtil.getPriceString(restaurant));
    mViewBinding.fabShowRatingDialog.setOnClickListener(v -> {
      if(mPresenter != null && mOnAddRating != null) {
        mOnAddRating.invoke();
      }
    });

    // Background image
    Glide.with(mViewBinding.restaurantImage.getContext())
        .load(restaurant.getPhoto())
        .into(mViewBinding.restaurantImage);
  }

}
