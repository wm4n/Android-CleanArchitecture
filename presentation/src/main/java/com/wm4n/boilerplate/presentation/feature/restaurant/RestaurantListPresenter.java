package com.wm4n.boilerplate.presentation.feature.restaurant;

import com.wm4n.boilerplate.domain.feature.restaurant.interactor.GetRestaurantList;
import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.domain.model.PaginationList;
import com.wm4n.boilerplate.presentation.internal.di.PerActivity;
import com.wm4n.boilerplate.presentation.navigation.Navigator;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

@PerActivity
public class RestaurantListPresenter implements RestaurantListContract.Presenter {

  private final Navigator mNavigator;
  private final GetRestaurantList mGetRestaurantListUseCase;

  private RestaurantListContract.View mView;

  private PaginationList<Restaurant> mQueryResult;

  @Inject
  RestaurantListPresenter(
      Navigator navigator,
      GetRestaurantList getRestaurantList) {
    mNavigator = navigator;
    mGetRestaurantListUseCase = getRestaurantList;
  }

  @Override
  public void setView(RestaurantListContract.View view) {
    mView = view;
  }

  @Override
  public void viewReady() {
    load(1);
  }

  @Override
  public void viewDestroy() {
  }

  @Override
  public void cleanup() {
    mGetRestaurantListUseCase.dispose();
  }

  private void load(final int p) {
    if(mView == null) {
      return;
    }
    if(mQueryResult == null || p == 1) {
      mView.renderContentLoading();
    }
    mGetRestaurantListUseCase.execute(new DisposableObserver<PaginationList<Restaurant>>() {
      @Override
      public void onNext(@NonNull PaginationList<Restaurant> result) {
        if(mQueryResult == null || p == 1) {
          mQueryResult = result;
        }
        else {
          mQueryResult.merge(result);
        }

        if(mView != null) {
          boolean hasMorePage = mQueryResult.hasMorePage();
          mView.renderList(
              mQueryResult.getContent(),
              hasMorePage,
              restaurant ->  {
                if(mView != null && mView.context() != null) {
                  mNavigator.toRestaurantDetails(mView.context(), restaurant.getId());
                }
              },
              () -> {
                if(hasMorePage) {
                  load(p + 1);
                }
              },
              () -> load(1));
        }
      }

      @Override
      public void onError(@NonNull Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    }, GetRestaurantList.Params.forPage(p));
  }
}
