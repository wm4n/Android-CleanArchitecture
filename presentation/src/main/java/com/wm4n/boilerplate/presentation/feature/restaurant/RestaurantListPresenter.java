package com.wm4n.boilerplate.presentation.feature.restaurant;

import com.wm4n.boilerplate.domain.feature.restaurant.interactor.GetRestaurantList;
import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.domain.model.PaginationList;
import com.wm4n.boilerplate.presentation.internal.di.PerActivity;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

@PerActivity
public class RestaurantListPresenter implements RestaurantListContract.Presenter {

  private final GetRestaurantList mGetRestaurantListUseCase;

  private RestaurantListContract.View mView;

  private int page = 0;
  private int totalPage = 0;
  private PaginationList<Restaurant> mQueryResult;

  @Inject
  RestaurantListPresenter(GetRestaurantList getRestaurantList) {
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
    mGetRestaurantListUseCase.execute(new DisposableObserver<PaginationList<Restaurant>>() {
      @Override
      public void onNext(@NonNull PaginationList<Restaurant> result) {
        if(mQueryResult == null || p == 0) {
          mQueryResult = result;
        }
        else {
          mQueryResult.merge(result);
        }

        if(mView != null) {
          mView.renderList(
              mQueryResult.getContent(),
              page != totalPage,
              null,
              () -> load(p + 1),
              () -> load(1));
        }
        page = mQueryResult.getPage();
        totalPage = mQueryResult.getPageCount();
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
