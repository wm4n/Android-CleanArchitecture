package com.wm4n.boilerplate.app_core.cloud_message;

public class RemoteMessageEvent<T> {

  private T remoteMessage;

  public RemoteMessageEvent(T remoteMessage) {
    this.remoteMessage = remoteMessage;
  }

  public boolean hasMessage() {
    return remoteMessage != null;
  }

  public T getRemoteMessage() {
    return remoteMessage;
  }

  public void setRemoteMessage(T remoteMessage) {
    this.remoteMessage = remoteMessage;
  }

  public String toString() {
    if (remoteMessage == null) {
      return "Remote message data is null";
    }
    return remoteMessage.toString();
  }
}