package com.wm4n.boilerplate.data.feature.restaurant.mapper;

import com.wm4n.boilerplate.data.entity.PaginationEntity;
import com.wm4n.boilerplate.data.feature.restaurant.entity.RestaurantEntity;
import com.wm4n.boilerplate.data.util.EntityDataMapperUtil;
import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.domain.model.PaginationList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link RestaurantEntity} (in the data layer) to {@link Restaurant} in the
 * domain layer.
 */
@Singleton
public class RestaurantEntityDataMapper {

  @Inject
  RestaurantEntityDataMapper() {
  }

  /**
   * Transform a {@link RestaurantEntity} into an {@link Restaurant}.
   *
   * @param entity Object to be transformed.
   * @return {@link Restaurant} if valid {@link RestaurantEntity} otherwise null.
   */
  public Restaurant transform(RestaurantEntity entity) {
    Restaurant restaurant = null;
    if (entity != null) {
      restaurant = new Restaurant(entity.getId());
      restaurant.setName(entity.getName());
      restaurant.setCity(entity.getCity());
      restaurant.setCategory(entity.getCategory());
      restaurant.setPhoto(entity.getPhoto());
      restaurant.setPrice(entity.getPrice());
      restaurant.setNumRatings(entity.getNumRatings());
      restaurant.setAvgRating(entity.getAvgRating());
    }
    return restaurant;
  }

  /**
   * Transform a List of {@link RestaurantEntity} into a Collection of {@link Restaurant}.
   *
   * @param entityList Object Collection to be transformed.
   * @return {@link Restaurant} if valid {@link RestaurantEntity} otherwise null.
   */
  public PaginationList<Restaurant> transform(PaginationEntity<RestaurantEntity> entityList) {
    return EntityDataMapperUtil.transformPaginationEntity(
        entityList,
        this::transform);
  }
}
