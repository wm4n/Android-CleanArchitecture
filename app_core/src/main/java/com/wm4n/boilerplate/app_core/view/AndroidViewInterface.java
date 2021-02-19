package com.wm4n.boilerplate.app_core.view;

import android.app.Activity;
import android.content.Context;

public interface AndroidViewInterface {

  int VIEW_OK = Activity.RESULT_OK;
  int VIEW_CANCELED = Activity.RESULT_CANCELED;

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
