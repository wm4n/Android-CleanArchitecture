package com.wm4n.boilerplate.domain.model;

import java.util.Collections;
import java.util.List;

public class PaginationList<T> implements Mergeable<PaginationList<T>> {

  private int recordCount;

  private int pageSize;

  private int page;

  private int pageCount;

  private List<T> content;

  public PaginationList() {
    recordCount = 0;
    pageSize = 0;
    page = 0;
    pageCount = 0;
    content = Collections.emptyList();
  }

  public PaginationList(
      int recordCount, int pageSize, int page, int pageCount, List<T> content) {
    this.recordCount = recordCount;
    this.pageSize = pageSize;
    this.page = page;
    this.pageCount = pageCount;
    this.content = content;
  }

  public int getRecordCount() {
    return recordCount;
  }

  public void setRecordCount(int recordCount) {
    this.recordCount = recordCount;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPageCount() {
    return pageCount;
  }

  public boolean hasMorePage() {
    return page < pageCount;
  }

  public void setPageCount(int pageCount) {
    this.pageCount = pageCount;
  }

  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }

  // Implementation
  public void merge(PaginationList<T> target) {
    recordCount = target.getRecordCount();
    pageSize = target.getPageSize();
    page = target.getPage();
    pageCount = target.getPageCount();
    if (content != null) {
      content.addAll(target.getContent());
    }
  }
}