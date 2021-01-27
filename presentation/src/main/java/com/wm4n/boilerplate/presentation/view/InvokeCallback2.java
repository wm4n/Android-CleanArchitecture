package com.wm4n.boilerplate.presentation.view;

/**
 * A simple callback interface with 2 parameters
 *
 * @param <T> parameter #1
 * @param <U> parameter #2
 */
public interface InvokeCallback2<T, U> {
  void invoke(T t, U u);
}
