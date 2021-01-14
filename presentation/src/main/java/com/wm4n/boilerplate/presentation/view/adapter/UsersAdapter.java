/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.wm4n.boilerplate.presentation.view.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wm4n.boilerplate.presentation.databinding.RowUserBinding;
import com.wm4n.boilerplate.presentation.model.UserModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

/**
 * Adaptar that manages a collection of {@link UserModel}.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

  public interface OnItemClickListener {
    void onUserItemClicked(UserModel userModel);
  }

  private List<UserModel> usersCollection;
  private final LayoutInflater layoutInflater;

  private OnItemClickListener onItemClickListener;

  @Inject
  UsersAdapter(Context context) {
    this.layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.usersCollection = Collections.emptyList();
  }

  @Override public int getItemCount() {
    return (this.usersCollection != null) ? this.usersCollection.size() : 0;
  }

  @Override public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    RowUserBinding viewBinding = RowUserBinding.inflate(layoutInflater, parent, false);
    return new UserViewHolder(viewBinding);
  }

  @Override public void onBindViewHolder(UserViewHolder holder, final int position) {
    final UserModel userModel = this.usersCollection.get(position);
    holder.viewBinding.title.setText(userModel.getFullName());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (UsersAdapter.this.onItemClickListener != null) {
          UsersAdapter.this.onItemClickListener.onUserItemClicked(userModel);
        }
      }
    });
  }

  @Override public long getItemId(int position) {
    return position;
  }

  public void setUsersCollection(Collection<UserModel> usersCollection) {
    this.validateUsersCollection(usersCollection);
    this.usersCollection = (List<UserModel>) usersCollection;
    this.notifyDataSetChanged();
  }

  public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  private void validateUsersCollection(Collection<UserModel> usersCollection) {
    if (usersCollection == null) {
      throw new IllegalArgumentException("The list cannot be null");
    }
  }

  static class UserViewHolder extends RecyclerView.ViewHolder {

    RowUserBinding viewBinding;

    UserViewHolder(RowUserBinding viewBinding) {
      super(viewBinding.getRoot());
      this.viewBinding = viewBinding;
    }
  }
}
