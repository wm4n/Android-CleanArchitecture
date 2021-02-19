package com.wm4n.boilerplate.app_core.service;

/**
 * The primary App Core function let modules know what services that the application
 * provide. For example, the application might provide Cloud Message service and
 * this provides an abstraction to the underlying implementation (It could be Google
 * FCM or Azure).
 */
public interface ServiceProvider {

  /**
   * Service that provides navigation ability within the application
   *
   * @return Navigation service object
   */
  NavigatorService getNavigatorService();

  /**
   * Service that provides cloud messaging ability such as getting push token
   *
   * @return Cloud Message service object
   */
  CloudMessageService getCloudMessageService();

  /**
   * Service that provides image loading ability such as Glide, Picasa, etc.
   *
   * @return Image loading service object
   */
  ImageLoadingService getImageLoadingService();

}