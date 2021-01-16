package com.wm4n.boilerplate.data.feature.restaurant.repository.datasource;

import android.content.Context;

import androidx.annotation.NonNull;

import com.wm4n.boilerplate.data.entity.PaginationEntity;
import com.wm4n.boilerplate.data.feature.restaurant.entity.RestaurantEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * {@link RestaurantDataStore} implementation based on file system data store.
 */
class RandomRestaurantDataStore implements RestaurantDataStore {

  private final List<RestaurantEntity> restaurantEntityList = new ArrayList<>();
  private final Map<String, RestaurantEntity> restaurantEntityMap = new HashMap<>();
  private final int dataCount;
  private static final int PAGE_SIZE = 20;

  /**
   * Construct a {@link RestaurantDataStore} based random generated data store.
   */
  RandomRestaurantDataStore(@NonNull Context context) {
    dataCount = (int)(Math.random() * 50 + 10);
    for(int i = 0; i < dataCount; i ++) {
      RestaurantEntity restaurant = RestaurantUtil.getRandom(context);
      restaurantEntityList.add(restaurant);
      restaurantEntityMap.put(restaurant.getId(), restaurant);
    }
  }

  @Override public Observable<PaginationEntity<RestaurantEntity>> restaurantEntityList(int page) {
    // add random delay to make it looks like web API request so we can observe the progressing
    final int randomDelay = (int)(Math.random() * 2000) + 100;
    // adjust page
    if(page < 1) page = 1;
    int start = (page - 1) * PAGE_SIZE < dataCount ? (page - 1) * PAGE_SIZE : 0;
    int end = Math.min(page * PAGE_SIZE, dataCount);
    PaginationEntity<RestaurantEntity> paginationEntity = new PaginationEntity<>();
    paginationEntity.setPage((page - 1) * PAGE_SIZE < dataCount ? page : 0);
    paginationEntity.setPageCount(dataCount / PAGE_SIZE + (dataCount % PAGE_SIZE == 0 ? 0 : 1));
    paginationEntity.setPageSize(PAGE_SIZE);
    paginationEntity.setList(restaurantEntityList.subList(start, end));
    return Observable.just(paginationEntity).delay(randomDelay, TimeUnit.MILLISECONDS);
  }

  @Override public Observable<RestaurantEntity> restaurantEntityDetails(final String id) {
    // add random delay to make it looks like web API request so we can observe the progressing
    final int randomDelay = (int)(Math.random() * 1000) + 100;
     return Observable.just(restaurantEntityMap.get(id)).delay(randomDelay, TimeUnit.MILLISECONDS);
  }
}
