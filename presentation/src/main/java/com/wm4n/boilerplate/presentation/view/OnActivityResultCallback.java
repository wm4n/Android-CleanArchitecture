package com.wm4n.boilerplate.presentation.view;

import android.content.Intent;

public interface OnActivityResultCallback {

  void onActivityResult(Integer requestCode, Integer resultCode, Intent data);
}
