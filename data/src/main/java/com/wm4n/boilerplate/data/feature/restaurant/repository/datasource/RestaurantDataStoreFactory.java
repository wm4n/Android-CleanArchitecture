package com.wm4n.boilerplate.data.feature.restaurant.repository.datasource;

import android.content.Context;

import androidx.annotation.NonNull;

import com.wm4n.boilerplate.data.feature.restaurant.cache.RestaurantCache;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link RestaurantDataStore}.
 */
@Singleton
public class RestaurantDataStoreFactory {

  private final Context context;
  private final RestaurantCache cache;
  private RestaurantDataStore cacheDataStore;
  private RestaurantDataStore randomDataStore;

  @Inject
  RestaurantDataStoreFactory(@NonNull Context context, @NonNull RestaurantCache cache) {
    this.context = context.getApplicationContext();
    this.cache = cache;
  }

  /**
   * Create {@link RestaurantDataStore} from a restaurant id.
   */
  public RestaurantDataStore create(String id) {
    RestaurantDataStore restaurantDataStore;

    if (!this.cache.isExpired() && this.cache.isCached(id)) {
      if (cacheDataStore == null) {
        cacheDataStore = new CacheRestaurantDataStore(cache);
      }
      restaurantDataStore = cacheDataStore;
    } else {
      if (randomDataStore == null) {
        randomDataStore = new RandomRestaurantDataStore(context, cache);
      }
      restaurantDataStore = randomDataStore;
    }

    return restaurantDataStore;
  }

  /**
   * Create {@link RestaurantDataStore} to generated random data.
   */
  public RestaurantDataStore createRandomDataStore() {
    if (randomDataStore == null) {
      randomDataStore = new RandomRestaurantDataStore(context, cache);
    }
    return randomDataStore;
  }
}
