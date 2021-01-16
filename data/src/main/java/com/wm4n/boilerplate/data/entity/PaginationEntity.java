package com.wm4n.boilerplate.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaginationEntity<T> {

  @SerializedName("PAGE")
  private int page;

  @SerializedName("PAGE_SIZE")
  private int pageSize;

  @SerializedName("PAGE_COUNT")
  private int pageCount;

  @SerializedName("RECORD_COUNT")
  private int recordCount;

  @SerializedName("LIST")
  private List<T> list;

  public int getPage() { return this.page; }

  public void setPage(int page) { this.page = page; }

  public int getPageSize() { return this.pageSize; }

  public void setPageSize(int pageSize) { this.pageSize = pageSize; }

  public int getPageCount() { return this.pageCount; }

  public void setPageCount(int pageCount) { this.pageCount = pageCount; }

  public int getRecordCount() { return this.recordCount; }

  public void setRecordCount(int recordCount) { this.recordCount = recordCount; }

  public List<T> getList() { return this.list; }

  public void setList(List<T> list) { this.list = list; }

}
