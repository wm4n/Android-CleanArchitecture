package com.wm4n.boilerplate.data.feature.restaurant.repository;

import com.wm4n.boilerplate.data.feature.restaurant.mapper.RestaurantEntityDataMapper;
import com.wm4n.boilerplate.data.feature.restaurant.repository.datasource.RestaurantDataStore;
import com.wm4n.boilerplate.data.feature.restaurant.repository.datasource.RestaurantDataStoreFactory;
import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.domain.feature.restaurant.repository.RestaurantRepository;
import com.wm4n.boilerplate.domain.model.PaginationList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link RestaurantRepository} for retrieving restaurant data.
 */
@Singleton
public class RestaurantDataRepository implements RestaurantRepository {

  private final RestaurantDataStoreFactory dataStoreFactory;
  private final RestaurantEntityDataMapper entityDataMapper;

  /**
   * Constructs a {@link RestaurantRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param entityDataMapper {@link RestaurantEntityDataMapper}.
   */
  @Inject
  RestaurantDataRepository(RestaurantDataStoreFactory dataStoreFactory,
                           RestaurantEntityDataMapper entityDataMapper) {
    this.dataStoreFactory = dataStoreFactory;
    this.entityDataMapper = entityDataMapper;
  }

  @Override
  public Observable<PaginationList<Restaurant>> getRestaurantList(int page) {
    final RestaurantDataStore dataStore = this.dataStoreFactory.createRandomDataStore();
    return dataStore.restaurantEntityList(page).map(entityDataMapper::transform);
  }

  @Override
  public Observable<Restaurant> getRestaurant(String id) {
    final RestaurantDataStore dataStore = this.dataStoreFactory.create(id);
    return dataStore.restaurantEntityDetails(id).map(entityDataMapper::transform);
  }
}
