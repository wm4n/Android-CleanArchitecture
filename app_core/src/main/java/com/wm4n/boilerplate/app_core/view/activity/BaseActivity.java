package com.wm4n.boilerplate.app_core.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.wm4n.boilerplate.app_core.view.InvokeCallback;
import com.wm4n.boilerplate.app_core.view.OnActivityResultCallback;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

  private static final String ACTION_SIMPLE_POPUP = "ACTION_SIMPLE_POPUP";

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
   * @param failureText Failure text to display
   * @param buttonText View's action button label
   * @param callback View's action callback to bind with the action button
   */
  public void renderFailureView(
      @NonNull CharSequence failureText, @Nullable CharSequence buttonText, @Nullable InvokeCallback callback) {
  }

  /**
   * Render a popup error/failure display that does not interrupt user with specified text and button action
   *
   * @param failureText Failure text to display
   * @param buttonText View's action button label
   * @param callback View's action callback to bind with the action button
   */
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
