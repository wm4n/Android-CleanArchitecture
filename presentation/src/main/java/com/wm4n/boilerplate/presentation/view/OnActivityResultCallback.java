package com.wm4n.boilerplate.presentation.view;

import android.content.Intent;

/**
 * An interface to be invoked when an activity is returning results
 */
public interface OnActivityResultCallback {

  void onActivityResult(Integer requestCode, Integer resultCode, Intent data);
}
