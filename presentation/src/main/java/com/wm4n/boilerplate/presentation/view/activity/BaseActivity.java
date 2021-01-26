package com.wm4n.boilerplate.presentation.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.wm4n.boilerplate.presentation.AndroidApplication;
import com.wm4n.boilerplate.presentation.internal.di.components.ApplicationComponent;
import com.wm4n.boilerplate.presentation.internal.di.modules.ActivityModule;
import com.wm4n.boilerplate.presentation.view.InvokeCallback;
import com.wm4n.boilerplate.presentation.view.OnActivityResultCallback;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

  private static final String ACTION_SIMPLE_POPUP = "ACTION_SIMPLE_POPUP";

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment) {
    final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((AndroidApplication) getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   * @return {@link ActivityModule}
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  /**
   * Render a SnackBar message for a short period
   *
   * @param message message to display
   */
  protected Snackbar renderSnackBarMessage(CharSequence message) {
    View v = getCurrentFocus();
    if (v != null) {
      final Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
      snackbar.show();
      return snackbar;
    }
    return null;
  }

  /**
   * Get application context
   *
   * @return application context
   */
  public Context context() {
    return getApplicationContext();
  }

  /**
   * Get Activity itself
   *
   * @return activity
   */
  public Activity activity() {
    return this;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Check actions that can be handled here
    if (resultCode == RESULT_OK) {
      if (data != null && data.hasExtra(ACTION_SIMPLE_POPUP)) {
        CharSequence message = data.getCharSequenceExtra(ACTION_SIMPLE_POPUP);
        if (message == null) {
          message = data.getStringExtra(ACTION_SIMPLE_POPUP);
        }
        renderSnackBarMessage(message);
      }
    }

    final OnActivityResultCallback callback = mOnActivityResultCallbackList.get(requestCode);
    if (callback != null) {
      callback.onActivityResult(requestCode, resultCode, data);
    }
  }

  /**
   * Render a full page error/failure screen with specified text and button action
   *
   * @param failureText Primary failure text to display
   * @param buttonText
   * @param callback
   */
  public void renderFailureView(
      @NonNull CharSequence failureText, @Nullable CharSequence buttonText, @Nullable InvokeCallback callback) {
  }


  public void renderPopupView(
      @NonNull CharSequence failureText, @Nullable CharSequence buttonText, @Nullable InvokeCallback callback) {
    View view = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    if(view != null) {
      Snackbar snackbar = Snackbar.make(view, failureText, Snackbar.LENGTH_SHORT);
      if (!TextUtils.isEmpty(buttonText) && callback != null) {
        snackbar.setAction(buttonText, v -> callback.invoke());
      }
      snackbar.show();
    }
  }

  /**
   * For storing onActivityResult's callback
   */
  private final SparseArray<OnActivityResultCallback> mOnActivityResultCallbackList =
      new SparseArray<>(5);

  /**
   * For storing onActivityResult's callback
   */
  @CallSuper
  public void setActivityResultCallback(Integer requestCode, OnActivityResultCallback callback) {
    mOnActivityResultCallbackList.put(requestCode, callback);
  }

  /**
   * Implementation of BaseViewInterface's setReturnResult
   *
   * @param resultCode Activity's resultCode
   * @param data data to pass back to onActivityResult
   */
  public void setReturnResult(int resultCode, Intent data) {
    setResult(resultCode, data);
  }

  public void closeView(Integer resultCode) {
    if (resultCode != null) {
      setResult(resultCode);
    }
    finish();
  }



}
