/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fernandocejas.android10.sample.presentation.databinding.FragmentUserDetailsBinding;
import com.fernandocejas.android10.sample.presentation.internal.di.components.UserComponent;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.presenter.UserDetailsPresenter;
import com.fernandocejas.android10.sample.presentation.view.UserDetailsView;
import com.fernandocejas.arrow.checks.Preconditions;
import javax.inject.Inject;

/**
 * Fragment that shows details of a certain user.
 */
public class UserDetailsFragment extends BaseFragment implements UserDetailsView {
  private static final String PARAM_USER_ID = "param_user_id";

  @Inject UserDetailsPresenter userDetailsPresenter;

  private FragmentUserDetailsBinding mViewBinding;

  public static UserDetailsFragment forUser(int userId) {
    final UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
    final Bundle arguments = new Bundle();
    arguments.putInt(PARAM_USER_ID, userId);
    userDetailsFragment.setArguments(arguments);
    return userDetailsFragment;
  }

  public UserDetailsFragment() {
    setRetainInstance(true);
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(UserComponent.class).inject(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mViewBinding = FragmentUserDetailsBinding.inflate(inflater, container, false);
    return mViewBinding.getRoot();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.userDetailsPresenter.setView(this);
    if (savedInstanceState == null) {
      this.loadUserDetails();
    }

    if(mViewBinding != null) {
      mViewBinding.viewRetry.rlRetry.setOnClickListener(v -> {
        if (this.userDetailsPresenter != null) {
          this.userDetailsPresenter.initialize(currentUserId());
        }
      });
    }
  }

  @Override public void onResume() {
    super.onResume();
    this.userDetailsPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.userDetailsPresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if(mViewBinding != null) {
      mViewBinding.unbind();
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    this.userDetailsPresenter.destroy();
  }

  @Override public void renderUser(UserModel user) {
    if (user != null && mViewBinding != null) {
      mViewBinding.viewUserDetails.ivCover.setImageUrl(user.getCoverUrl());
      mViewBinding.viewUserDetails.tvFullname.setText(user.getFullName());
      mViewBinding.viewUserDetails.tvEmail.setText(user.getEmail());
      mViewBinding.viewUserDetails.tvFollowers.setText(String.valueOf(user.getFollowers()));
      mViewBinding.viewUserDetails.tvDescription.setText(user.getDescription());
    }
  }

  @Override public void showLoading() {
    if(mViewBinding != null) {
      mViewBinding.viewProgress.rlProgress.setVisibility(View.VISIBLE);
    }
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    if(mViewBinding != null) {
      mViewBinding.viewProgress.rlProgress.setVisibility(View.GONE);
    }
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override public void showRetry() {
    if(mViewBinding != null) {
      mViewBinding.viewRetry.rlRetry.setVisibility(View.VISIBLE);
    }
  }

  @Override public void hideRetry() {
    if(mViewBinding != null) {
      mViewBinding.viewRetry.rlRetry.setVisibility(View.GONE);
    }
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context context() {
    return getActivity().getApplicationContext();
  }

  /**
   * Load user details.
   */
  private void loadUserDetails() {
    if (this.userDetailsPresenter != null) {
      this.userDetailsPresenter.initialize(currentUserId());
    }
  }

  /**
   * Get current user id from fragments arguments.
   */
  private int currentUserId() {
    final Bundle arguments = getArguments();
    Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
    return arguments.getInt(PARAM_USER_ID);
  }
}
