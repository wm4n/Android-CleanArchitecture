/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.wm4n.boilerplate.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wm4n.boilerplate.presentation.databinding.FragmentUserListBinding;
import com.wm4n.boilerplate.presentation.internal.di.components.UserComponent;
import com.wm4n.boilerplate.presentation.model.UserModel;
import com.wm4n.boilerplate.presentation.presenter.UserListPresenter;
import com.wm4n.boilerplate.presentation.view.UserListView;
import com.wm4n.boilerplate.presentation.view.adapter.UsersAdapter;
import com.wm4n.boilerplate.presentation.view.adapter.UsersLayoutManager;

import java.util.Collection;
import javax.inject.Inject;

/**
 * Fragment that shows a list of Users.
 */
public class UserListFragment extends BaseFragment implements UserListView {

  /**
   * Interface for listening user list events.
   */
  public interface UserListListener {
    void onUserClicked(final UserModel userModel);
  }

  @Inject
  UserListPresenter userListPresenter;
  @Inject
  UsersAdapter usersAdapter;

  private UserListListener userListListener;
  private FragmentUserListBinding mViewBinding;

  public UserListFragment() {
    setRetainInstance(true);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof UserListListener) {
      this.userListListener = (UserListListener) activity;
    }
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(UserComponent.class).inject(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mViewBinding = FragmentUserListBinding.inflate(inflater, container, false);
    setupRecyclerView();
    return mViewBinding.getRoot();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.userListPresenter.setView(this);
    if (savedInstanceState == null) {
      this.loadUserList();
    }

    if(mViewBinding != null) {
      mViewBinding.viewRetry.btRetry.setOnClickListener(v -> {
        UserListFragment.this.loadUserList();
      });
    }
    UserListFragment.this.loadUserList();
  }

  @Override public void onResume() {
    super.onResume();
    this.userListPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
    this.userListPresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if(mViewBinding != null) {
      mViewBinding.rvUsers.setAdapter(null);
      mViewBinding.unbind();
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    this.userListPresenter.destroy();
  }

  @Override public void onDetach() {
    super.onDetach();
    this.userListListener = null;
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

  @Override public void renderUserList(Collection<UserModel> userModelCollection) {
    if (userModelCollection != null) {
      this.usersAdapter.setUsersCollection(userModelCollection);
    }
  }

  @Override public void viewUser(UserModel userModel) {
    if (this.userListListener != null) {
      this.userListListener.onUserClicked(userModel);
    }
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context context() {
    return this.getActivity().getApplicationContext();
  }

  private void setupRecyclerView() {
    this.usersAdapter.setOnItemClickListener(onItemClickListener);
    if(mViewBinding != null) {
      mViewBinding.rvUsers.setLayoutManager(new UsersLayoutManager(context()));
      mViewBinding.rvUsers.setAdapter(usersAdapter);
    }
  }

  /**
   * Loads all users.
   */
  private void loadUserList() {
    this.userListPresenter.initialize();
  }

  private UsersAdapter.OnItemClickListener onItemClickListener =
      new UsersAdapter.OnItemClickListener() {
        @Override public void onUserItemClicked(UserModel userModel) {
          if (UserListFragment.this.userListPresenter != null && userModel != null) {
            UserListFragment.this.userListPresenter.onUserClicked(userModel);
          }
        }
      };
}
