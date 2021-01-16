package com.wm4n.boilerplate.domain.model;

public interface Mergeable<T> {

  void merge(T target);
}

