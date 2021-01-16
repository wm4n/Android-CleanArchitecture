package com.wm4n.boilerplate.data.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.annimon.stream.function.Function;
import com.wm4n.boilerplate.data.entity.PaginationEntity;
import com.wm4n.boilerplate.domain.model.PaginationList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EntityDataMapperUtil {

  private EntityDataMapperUtil() {
  }

  private static void bindPaginationEntity(
      @Nullable PaginationList<?> result, @Nullable PaginationEntity<?> entity) {
    if (result == null) return;
    if (entity == null) {
      result.setRecordCount(0);
      result.setPageSize(0);
      result.setPage(0);
      result.setPageCount(0);
    } else {
      result.setRecordCount(entity.getRecordCount());
      result.setPageSize(entity.getPageSize());
      result.setPage(entity.getPage());
      result.setPageCount(entity.getPageCount());
    }
  }

  public static <T, R> PaginationList<R> transformPaginationEntity(
      @Nullable PaginationEntity<T> paginationEntity, @NonNull Function<T, R> itemTransform) {
    PaginationList<R> paginationList = new PaginationList<>();
    bindPaginationEntity(paginationList, paginationEntity);

    if (paginationEntity != null) {
      final List<T> listEntity = paginationEntity.getList();
      List<R> listData = new ArrayList<>();
      if (listEntity != null) {
        for (T itemEntity : listEntity) {
          R item = itemTransform.apply(itemEntity);
          if(item != null) {
            listData.add(item);
          }
        }
      }
      paginationList.setContent(listData);
    } else {
      paginationList.setContent(Collections.emptyList());
    }
    return paginationList;
  }
}
