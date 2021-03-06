package com.wm4n.boilerplate.presentation.feature.main;

import android.os.Bundle;
import com.wm4n.boilerplate.presentation.databinding.ActivityMainBinding;
import com.wm4n.boilerplate.presentation.view.activity.PresentationBaseActivity;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends PresentationBaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
    if(viewBinding != null) {
      setContentView(viewBinding.getRoot());
      viewBinding.btnLoadData.setOnClickListener(v -> {
        getNavigator().toRestaurantList(this);
      });
    }
  }

}
