package com.wm4n.boilerplate.app_core.service;

import android.app.Application;

import com.wm4n.boilerplate.app_core.view.AndroidViewInterface;

/**
 * Service that provides navigation ability within the application
 */
public interface NavigatorService {

  static NavigatorService from(Application application) {
    if (application instanceof ServiceProvider) {
      return ((ServiceProvider) application).getNavigatorService();
    }
    return new NavigatorService() {
    };
  }

  static NavigatorService from(AndroidViewInterface androidViewInterface) {
    if (androidViewInterface != null && androidViewInterface.activity() != null) {
      if (androidViewInterface.activity().getApplication() instanceof ServiceProvider) {
        return ((ServiceProvider) androidViewInterface.activity().getApplication())
            .getNavigatorService();
      }
    }
    return new NavigatorService() {
    };
  }
}
