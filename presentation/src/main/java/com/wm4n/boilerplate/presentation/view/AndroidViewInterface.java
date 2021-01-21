package com.wm4n.boilerplate.presentation.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public interface AndroidViewInterface {

  int VIEW_OK = Activity.RESULT_OK;
  int VIEW_CANCELED = Activity.RESULT_CANCELED;
  int VIEW_FIRST_USER = Activity.RESULT_FIRST_USER;

  /**
   * Get the application {@link Context} of the view.
   *
   * @return Application {@link Context}
   */
  Context context();

  /**
   * Get the {@link Activity} of the view
   *
   * @return {@link Activity}
   */
  Activity activity();

  /**
   * Assign a callback for onActivityResult
   *
   * @param callback callback to invoke when onActivityResult is called
   */
  default void setActivityResultCallback(Integer requestCode, OnActivityResultCallback callback) {
  }

  /**
   * Assign a result code to the implemented {@link Activity}
   *
   * @param resultCode The result code to propagate back to the originating activity, often
   *                   RESULT_CANCELED or RESULT_OK
   */
  default void setViewResult(int resultCode) {
    if (activity() != null) {
      activity().setResult(resultCode);
    }
  }

  /**
   * Assign a result code and return data to the implemented {@link Activity}
   *
   * @param resultCode The result code to propagate back to the originating activity, often
   *                   RESULT_CANCELED or RESULT_OK
   * @param data       The data to propagate back to the originating activity.
   */
  default void setViewReturnResult(int resultCode, Intent data) {
  }

  /**
   * Close the current view by providing a generic interface. This might be finish for Activity,
   * pop for Fragment or dismiss for Dialog.
   */
  default void closeView(Integer resultCode) {
  }

  /**
   * Default behavior of checking whether the view still exist or not
   *
   * @return true if the view does not exist
   */
  default boolean isViewFinishing() {
    return activity() == null || activity().isFinishing();
  }
}
