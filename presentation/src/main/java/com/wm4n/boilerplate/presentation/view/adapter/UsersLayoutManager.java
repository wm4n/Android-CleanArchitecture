/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.wm4n.boilerplate.presentation.view.adapter;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Layout manager to position items inside a {@link RecyclerView}.
 */
public class UsersLayoutManager extends LinearLayoutManager {
  public UsersLayoutManager(Context context) {
    super(context);
  }
}
