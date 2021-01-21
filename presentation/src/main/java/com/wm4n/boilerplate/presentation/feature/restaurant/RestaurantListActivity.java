package com.wm4n.boilerplate.presentation.feature.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wm4n.boilerplate.domain.feature.restaurant.model.Restaurant;
import com.wm4n.boilerplate.presentation.AndroidApplication;
import com.wm4n.boilerplate.presentation.databinding.RestaurantListBinding;
import com.wm4n.boilerplate.presentation.internal.di.components.DaggerUserComponent;
import com.wm4n.boilerplate.presentation.internal.di.components.UserComponent;
import com.wm4n.boilerplate.presentation.internal.di.modules.ActivityModule;
import com.wm4n.boilerplate.presentation.view.InvokeCallback;
import com.wm4n.boilerplate.presentation.view.InvokeCallback1;
import com.wm4n.boilerplate.presentation.view.activity.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

public class RestaurantListActivity extends BaseActivity implements RestaurantListContract.View {

  public static Intent getLaunchIntent(Context context) {
    return new Intent(context, RestaurantListActivity.class);
  }

  private RestaurantListBinding mViewBinding;
  private RestaurantAdapter mAdapter;
  private InvokeCallback1<Restaurant> mOnSelectedCallback;
  private InvokeCallback mLoadNextPage;
  private InvokeCallback mOnRefreshList;

  private UserComponent userComponent;

  @Inject RestaurantListPresenter mPresenter;

  private void initializeInjector() {
    this.userComponent = DaggerUserComponent.builder()
        .applicationComponent(((AndroidApplication) getApplication()).getApplicationComponent())
        .activityModule(new ActivityModule(this))
        .build();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    initializeInjector();
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
    userComponent.inject(this);

    mViewBinding = RestaurantListBinding.inflate(getLayoutInflater());
    setContentView(mViewBinding.getRoot());

    initRecyclerView();
    mPresenter.setView(this);

    Looper.myQueue()
        .addIdleHandler(
            () -> {
              if(mPresenter != null) {
                mPresenter.viewReady();
              }
              return false;
            });
  }

  @Override
  protected void onDestroy() {
    mPresenter.viewDestroy();
    mPresenter.cleanup();
    super.onDestroy();
  }

  private void initRecyclerView() {
    mAdapter = new RestaurantAdapter();
    mAdapter.setListener(() -> {
      if(mLoadNextPage != null) {
        mLoadNextPage.invoke();
      }
    });
    mAdapter.setListener(restaurant -> {
      if(mOnSelectedCallback != null) {
        mOnSelectedCallback.invoke(restaurant);
      }
    });

    mViewBinding.list.setLayoutManager(new LinearLayoutManager(this));
    mViewBinding.list.setAdapter(mAdapter);

    mViewBinding.swipeRefresh.setProgressViewOffset(false, 0, 500);
    mViewBinding.swipeRefresh.setOnRefreshListener(() -> {
      if(mOnRefreshList != null) {
        mOnRefreshList.invoke();
      }
    });
  }

  @Override
  public void renderContentLoading() {
    mViewBinding.swipeRefresh.setRefreshing(true);
  }

  @Override
  public void renderList(
      @NonNull List<Restaurant> data,
      boolean hasMorePage,
      @Nullable InvokeCallback1<Restaurant> onSelected,
      @Nullable InvokeCallback onLoadNextPage,
      @Nullable InvokeCallback onRefreshList) {
    mViewBinding.swipeRefresh.setRefreshing(false);
    mOnSelectedCallback = onSelected;
    mLoadNextPage = onLoadNextPage;
    mOnRefreshList = onRefreshList;
    if(mAdapter != null) {
      mAdapter.updateData(data, hasMorePage);
      mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
    }
  }

  @Override
  public Context context() {
    return this;
  }
}
