package com.wm4n.boilerplate.presentation.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface BaseViewInterface extends AndroidViewInterface {

  /**
   * Render full page failure view (If view supported)
   *
   * @param failureText primary failure message
   * @param buttonText optional action button text
   * @param callback optional handler to perform upon the action button is invoked. If null
   *     is given, the button should hide
   */
  default void renderFailureView(
      @NonNull CharSequence failureText, @Nullable CharSequence buttonText, @Nullable InvokeCallback callback) {}

  /**
   * Render a popup failure view without interrupting the current displaying view (if view supported)
   *
   * @param failureText primary failure message
   * @param buttonText optional action button text
   * @param callback optional handler to perform upon the action button is invoked. If null
   *     is given, the button should hide
   */
  default void renderPopupView(
      @NonNull CharSequence failureText, @Nullable CharSequence buttonText, @Nullable InvokeCallback callback) {}

}
