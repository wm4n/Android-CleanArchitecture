package com.wm4n.boilerplate.app_core.service;

import android.app.Application;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.wm4n.boilerplate.app_core.view.AndroidViewInterface;

/**
 * Service that provides image loading ability such as Glide, Picasa, etc.
 */
public interface ImageLoadingService {

  static ImageLoadingService from(Application application) {
    if (application instanceof ServiceProvider) {
      return ((ServiceProvider) application).getImageLoadingService();
    }
    return new ImageLoadingService() {};
  }

  static ImageLoadingService from(AndroidViewInterface androidViewInterface) {
    if (androidViewInterface != null && androidViewInterface.activity() != null) {
      if (androidViewInterface.activity().getApplication() instanceof ServiceProvider) {
        return ((ServiceProvider) androidViewInterface.activity().getApplication())
            .getImageLoadingService();
      }
    }
    return new ImageLoadingService() {};
  }

  /**
   * Load image from url into an ImageView
   *
   * @param url Image URL
   * @param imageView target view
   */
  default void loadImage(String url, ImageView imageView) {}

  /**
   * Load image from a bitmap into an ImageView
   *
   * @param bmp Image bitmap
   * @param imageView target view
   */
  default void loadImage(Bitmap bmp, ImageView imageView) {}

  /**
   * Load image from a resource ID into an ImageView
   *
   * @param resource Image resource ID
   * @param imageView target view
   */
  default void loadImage(@DrawableRes int resource, ImageView imageView) {}

  /**
   * Load image from a resource ID into an ImageView
   *
   * @param resource Image resource ID
   * @param width Image width
   * @param height Image height
   * @param imageView target view
   */
  default void loadImage(@DrawableRes int resource, int width, int height, ImageView imageView) {}

  /**
   * Load image from a bitmap into an ImageView with placeholder and error drawable
   *
   * @param bmp Image bitmap
   * @param placeHolder Image to display before the actual image is loaded
   * @param errorDrawable Image to display if the actual image failed to load
   * @param imageView target view
   */
  default void loadImage(
      Bitmap bmp,
      @DrawableRes int placeHolder,
      @DrawableRes int errorDrawable,
      ImageView imageView) {}

  /**
   * Load image from a bitmap into an ImageView with placeholder
   *
   * @param bmp Image bitmap
   * @param placeHolder Image to display before the actual image is loaded
   * @param imageView target view
   */
  default void loadImage(
      Bitmap bmp,
      @DrawableRes int placeHolder,
      ImageView imageView) {}

  /**
   * Load image from a byte array into an ImageView with placeholder
   *
   * @param imgByteArr Image byte array
   * @param placeHolder Image to display before the actual image is loaded
   * @param errorDrawable Image to display if the actual image failed to load
   * @param imageView target view
   */
  default void loadImage(
      byte[] imgByteArr,
      @DrawableRes int placeHolder,
      @DrawableRes int errorDrawable,
      ImageView imageView) {}

  /**
   * Load image from an image URL into an ImageView with placeholder
   *
   * @param url Image URL
   * @param placeHolder Image to display before the actual image is loaded
   * @param errorDrawable Image to display if the actual image failed to load
   * @param imageView target view
   */
  default void loadImage(
      String url,
      @DrawableRes int placeHolder,
      @DrawableRes int errorDrawable,
      ImageView imageView) {}

  /**
   * Load GIF image from an image URL into an ImageView with placeholder
   *
   * @param url Image URL
   * @param placeHolder Image to display before the actual image is loaded
   * @param errorDrawable Image to display if the actual image failed to load
   * @param imageView target view
   */
  default void loadGifImage(
      String url,
      @DrawableRes int placeHolder,
      @DrawableRes int errorDrawable,
      ImageView imageView) {}

  /**
   * Load image from a byte array into an ImageView with placeholder
   *
   * @param imgByteArr Image byte array
   * @param placeHolder Image to display before the actual image is loaded
   * @param imageView target view
   */
  default void loadImage(
      byte[] imgByteArr,
      @DrawableRes int placeHolder,
      ImageView imageView) {}

  /**
   * Load GIF image from a byte array into an ImageView with placeholder
   *
   * @param imgByteArr Image byte array
   * @param placeHolder Image to display before the actual image is loaded
   * @param imageView target view
   */
  default void loadGifImage(
      byte[] imgByteArr,
      @DrawableRes int placeHolder,
      ImageView imageView) {}

  /**
   * Load image from a image URL into an ImageView with placeholder
   *
   * @param url Image URL
   * @param placeHolder Image to display before the actual image is loaded
   * @param imageView target view
   */
  default void loadImage(
      String url,
      @DrawableRes int placeHolder,
      ImageView imageView) {}

  /**
   * Load GIF image from a image URL into an ImageView with placeholder
   *
   * @param url Image URL
   * @param placeHolder Image to display before the actual image is loaded
   * @param imageView target view
   */
  default void loadGifImage(
      String url,
      @DrawableRes int placeHolder,
      ImageView imageView) {}

  /**
   * Remove image display on the target image view
   *
   * @param imageView target view
   */
  default void clearImage(
      ImageView imageView) {}
}