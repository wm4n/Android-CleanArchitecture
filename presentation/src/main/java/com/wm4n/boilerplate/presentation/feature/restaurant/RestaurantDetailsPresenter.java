package com.wm4n.boilerplate.presentation.feature.restaurant;

import com.wm4n.boilerplate.domain.feature.restaurant.interactor.GetRestaurantDetails;
import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.presentation.internal.di.PerActivity;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

@PerActivity
public class RestaurantDetailsPresenter implements RestaurantDetailsContract.Presenter {

  private final GetRestaurantDetails mGetRestaurantDetailsUseCase;

  private RestaurantDetailsContract.View mView;

  private String mRestaurantId;
  private Restaurant mRestaurant;

  @Inject
  RestaurantDetailsPresenter(GetRestaurantDetails getRestaurantDetails) {
    mGetRestaurantDetailsUseCase = getRestaurantDetails;
  }

  @Override
  public void setRestaurant(String restaurantId) {
    mRestaurantId = restaurantId;
  }

  @Override
  public void setView(RestaurantDetailsContract.View view) {
    mView = view;
  }

  @Override
  public void viewReady() {
    if(mRestaurantId != null) {
      load(mRestaurantId);
    }
  }

  @Override
  public void viewDestroy() {

  }

  @Override
  public void cleanup() {
    mGetRestaurantDetailsUseCase.dispose();
  }

  private void load(final String restaurantId) {
    if(mView == null) {
      return;
    }
    if(mRestaurant == null) {
      mView.renderContentLoading();
    }
    mGetRestaurantDetailsUseCase.execute(new DisposableObserver<Restaurant>() {
      @Override
      public void onNext(@NonNull Restaurant result) {
        mRestaurant = result;
        if(mView != null) {
          mView.renderDetail(
              mRestaurant,
              () -> {});
        }
      }

      @Override
      public void onError(@NonNull Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    }, GetRestaurantDetails.Params.forRestaurant(restaurantId));
  }
}
