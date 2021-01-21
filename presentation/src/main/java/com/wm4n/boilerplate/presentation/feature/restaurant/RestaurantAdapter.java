/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wm4n.boilerplate.presentation.feature.restaurant;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wm4n.boilerplate.data.feature.restaurant.repository.datasource.RestaurantUtil;
import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.presentation.R;
import com.wm4n.boilerplate.presentation.databinding.RestaurantLastListItemBinding;
import com.wm4n.boilerplate.presentation.databinding.RestaurantListItemBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RecyclerView adapter for a list of Restaurants.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public interface OnRestaurantSelectedListener {
    void onRestaurantSelected(Restaurant restaurant);
  }

  public interface OnRestaurantLoadMoreListener {
    void loadMore();
  }

  private final static int TYPE_NORMAL_ITEM = 0;
  private final static int TYPE_LAST_ITEM = 1;
  private List<Restaurant> mData = Collections.emptyList();
  private OnRestaurantSelectedListener mSelectListener;
  private OnRestaurantLoadMoreListener mLoadMoreListener;
  private boolean mHasMore = false;

  public RestaurantAdapter() {
    super();
  }

  public void updateData(List<Restaurant> data, boolean hasMore) {
    if (data == null) {
      mData = Collections.emptyList();
    } else {
      mData = new ArrayList<>(data);
    }
    mHasMore = hasMore;
    notifyDataSetChanged();
  }

  public void setListener(OnRestaurantSelectedListener listener) {
    mSelectListener = listener;
  }

  public void setListener(OnRestaurantLoadMoreListener listener) {
    mLoadMoreListener = listener;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if(viewType == TYPE_NORMAL_ITEM) {
      RestaurantListItemBinding binding = RestaurantListItemBinding.inflate(
          LayoutInflater.from(parent.getContext()),
          parent,
          false);
      return new RegularItemViewHolder(binding);
    }
    else {
      RestaurantLastListItemBinding binding = RestaurantLastListItemBinding.inflate(
          LayoutInflater.from(parent.getContext()),
          parent,
          false);
      return new LoadMoreItemViewHolder(binding);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if(holder instanceof RegularItemViewHolder) {
      ((RegularItemViewHolder)holder).bind(mData.get(position), mSelectListener);
    }
    else if(holder instanceof LoadMoreItemViewHolder) {
      ((LoadMoreItemViewHolder)holder).bind(mHasMore, mLoadMoreListener);
    }
  }

  @Override
  public int getItemCount() {
    int size = mData.size();
    return size > 0 ? size + 1 : size;
  }

  @Override
  public int getItemViewType(int position) {
    return mData.size() == position ? TYPE_LAST_ITEM : TYPE_NORMAL_ITEM;
  }

  static class LoadMoreItemViewHolder extends RecyclerView.ViewHolder {

    RestaurantLastListItemBinding binding;

    public LoadMoreItemViewHolder(RestaurantLastListItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(boolean hasMore,
                     final OnRestaurantLoadMoreListener listener) {

      Resources res = binding.text.getResources();
      binding.text.setText(
          hasMore ?
              res.getString(R.string.restaurant_load_more) :
              res.getString(R.string.restaurant_list_end));

      // Click listener
      itemView.setOnClickListener(view -> {
        if (listener != null && hasMore) {
          listener.loadMore();
        }
      });
    }
  }

  static class RegularItemViewHolder extends RecyclerView.ViewHolder {

    RestaurantListItemBinding binding;

    public RegularItemViewHolder(RestaurantListItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(final Restaurant restaurant,
                     final OnRestaurantSelectedListener listener) {

      Resources resources = itemView.getResources();
      // Load image
      Glide.with(binding.restaurantItemImage.getContext())
          .load(restaurant.getPhoto())
          .into(binding.restaurantItemImage);

      binding.restaurantItemName.setText(restaurant.getName());
      binding.restaurantItemRating.setRating((float) restaurant.getAvgRating());
      binding.restaurantItemCity.setText(restaurant.getCity());
      binding.restaurantItemCategory.setText(restaurant.getCategory());
      binding.restaurantItemNumRatings.setText(resources.getString(R.string.fmt_num_ratings,
          restaurant.getNumRatings()));
      binding.restaurantItemPrice.setText(RestaurantUtil.getPriceString(restaurant));

      // Click listener
      itemView.setOnClickListener(view -> {
        if (listener != null) {
          listener.onRestaurantSelected(restaurant);
        }
      });
    }

  }
}
