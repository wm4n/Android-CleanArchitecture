package com.wm4n.boilerplate.presentation.view.activity;

import android.os.Bundle;
import com.wm4n.boilerplate.presentation.databinding.ActivityMainBinding;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
    if(viewBinding != null) {
      setContentView(viewBinding.getRoot());
      viewBinding.btnLoadData.setOnClickListener(v -> {
        this.navigator.navigateToUserList(this);
      });
    }
  }

}
