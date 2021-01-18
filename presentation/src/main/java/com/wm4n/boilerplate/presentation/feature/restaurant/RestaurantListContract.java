package com.wm4n.boilerplate.presentation.feature.restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.presentation.view.InvokeCallback;
import com.wm4n.boilerplate.presentation.view.InvokeCallback1;

import java.util.List;

public interface RestaurantListContract {

  interface View {

    void renderList(
        @NonNull List<Restaurant> data,
        boolean hasMorePage,
        @Nullable InvokeCallback1<Restaurant> onSelected,
        @Nullable InvokeCallback onLoadNextPage,
        @Nullable InvokeCallback onRefreshList);
  }

  interface Presenter {

    void setView(View view);

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
  }

}
