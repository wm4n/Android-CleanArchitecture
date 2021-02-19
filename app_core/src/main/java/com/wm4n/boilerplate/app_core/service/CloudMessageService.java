package com.wm4n.boilerplate.app_core.service;

import android.app.Application;

import com.wm4n.boilerplate.app_core.cloud_message.RemoteMessageEvent;
import com.wm4n.boilerplate.app_core.view.AndroidViewInterface;

import io.reactivex.Observable;

public interface CloudMessageService {

  static CloudMessageService from(Application application) {
    if (application instanceof ServiceProvider) {
      return ((ServiceProvider) application).getCloudMessageService();
    }
    return new CloudMessageService() {};
  }

  static CloudMessageService from(AndroidViewInterface androidViewInterface) {
    if (androidViewInterface != null && androidViewInterface.activity() != null) {
      if (androidViewInterface.activity().getApplication() instanceof ServiceProvider) {
        return ((ServiceProvider) androidViewInterface.activity().getApplication()).getCloudMessageService();
      }
    }
    return new CloudMessageService() {};
  }

  /**
   * Retrieve cloud message's service token
   *
   * @return push token
   */
  default String getToken() {
    return "";
  }

  /**
   * Retrieve cloud message's service token {@link Observable}, subscription will get the current token
   *
   * @return push token {@link Observable}
   */
  default Observable<String> getTokenObservable() {
    return Observable.empty();
  }

  /**
   * Retrieve cloud message {@link RemoteMessageEvent}, subscription will get the latest message
   *
   * @return {@link RemoteMessageEvent}
   */
  default Observable<RemoteMessageEvent<Object>> getRemoteMessageObservable() {
    return Observable.empty();
  }

  /**
   * Retrieve cached cloud message {@link Object}， and delete afterward
   *
   * @param id id of the cached cloud message object
   * @return {@link Object}
   */
  default Object getRemoteMessageFor(long id) {
    return null;
  }
}
