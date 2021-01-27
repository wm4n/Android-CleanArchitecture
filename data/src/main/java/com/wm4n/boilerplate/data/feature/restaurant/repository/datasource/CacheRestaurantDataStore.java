package com.wm4n.boilerplate.data.feature.restaurant.repository.datasource;

import com.wm4n.boilerplate.data.entity.PaginationEntity;
import com.wm4n.boilerplate.data.feature.restaurant.cache.RestaurantCache;
import com.wm4n.boilerplate.data.feature.restaurant.entity.RestaurantEntity;

import io.reactivex.Observable;

/**
 * {@link RestaurantDataStore} implementation based on file system data store.
 */
class CacheRestaurantDataStore implements RestaurantDataStore {

  private final RestaurantCache cache;

  /**
   * Construct a {@link RestaurantDataStore} based file system data store.
   *
   * @param cache A {@link RestaurantCache} to cache data retrieved from the other source.
   */
  CacheRestaurantDataStore(RestaurantCache cache) {
    this.cache = cache;
  }

  @Override public Observable<PaginationEntity<RestaurantEntity>> restaurantEntityList(int page) {
    //TODO: implement simple cache for storing/retrieving collections of restaurant.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }

  @Override public Observable<RestaurantEntity> restaurantEntityDetails(final String id) {
     return this.cache.get(id);
  }
}
