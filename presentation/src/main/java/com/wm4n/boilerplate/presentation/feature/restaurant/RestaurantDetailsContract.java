package com.wm4n.boilerplate.presentation.feature.restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.presentation.view.BaseViewInterface;
import com.wm4n.boilerplate.presentation.view.InvokeCallback;

public interface RestaurantDetailsContract {

  interface View extends BaseViewInterface {

    void renderDetail(
        @NonNull Restaurant restaurant,
        @Nullable InvokeCallback onAddRating);
  }

  interface Presenter {

    void setRestaurant(String restaurantId);

    void setView(RestaurantDetailsContract.View view);

    /**
     * Method that indicates views are ready in the Activity or Fragment. Usually happens after bind
     * view or onViewCreated
     */
    void viewReady();

    /**
     * Method that indicates views are destroyed in the Activity or Fragment. Usually happens after
     * nullify or onViewDestroy
     */
    void viewDestroy();

    /** Method that indicates resources should be clean up */
    void cleanup();

    void onCloseClicked();

    void onAddRatingClicked();
  }

}
