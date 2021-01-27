/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wm4n.boilerplate.data.feature.restaurant.cache;

import android.content.Context;

import com.wm4n.boilerplate.data.cache.FileManager;
import com.wm4n.boilerplate.data.cache.serializer.Serializer;
import com.wm4n.boilerplate.data.exception.CacheNotFoundException;
import com.wm4n.boilerplate.data.feature.restaurant.entity.RestaurantEntity;
import com.wm4n.boilerplate.domain.executor.ThreadExecutor;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link RestaurantCache} implementation.
 */
@Singleton
public class RestaurantCacheImpl implements RestaurantCache {

  private static final String SETTINGS_FILE_NAME = "com.wm4n.boilerplate.data.restaurant.SETTINGS";
  private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

  private static final String DEFAULT_FILE_NAME = "restaurant_";
  private static final long EXPIRATION_TIME = 60 * 10 * 1000;

  private final Context context;
  private final File cacheDir;
  private final Serializer serializer;
  private final FileManager fileManager;
  private final ThreadExecutor threadExecutor;

  /**
   * Constructor of the class {@link RestaurantCacheImpl}.
   *
   * @param context A
   * @param serializer {@link Serializer} for object serialization.
   * @param fileManager {@link FileManager} for saving serialized objects to the file system.
   */
  @Inject
  RestaurantCacheImpl(Context context, Serializer serializer,
                      FileManager fileManager, ThreadExecutor executor) {
    if (context == null || serializer == null || fileManager == null || executor == null) {
      throw new IllegalArgumentException("Invalid null parameter");
    }
    this.context = context.getApplicationContext();
    this.cacheDir = this.context.getCacheDir();
    this.serializer = serializer;
    this.fileManager = fileManager;
    this.threadExecutor = executor;
  }

  @Override public Observable<RestaurantEntity> get(final String id) {
    return Observable.create(emitter -> {
      final File entityFile = RestaurantCacheImpl.this.buildFile(id);
      final String fileContent = RestaurantCacheImpl.this.fileManager.readFileContent(entityFile);
      final RestaurantEntity entity =
          RestaurantCacheImpl.this.serializer.deserialize(fileContent, RestaurantEntity.class);

      if (entity != null) {
        emitter.onNext(entity);
        emitter.onComplete();
      } else {
        emitter.onError(new CacheNotFoundException());
      }
    });
  }

  @Override public void put(RestaurantEntity entity) {
    if (entity != null) {
      final File entityFile = this.buildFile(entity.getId());
      if (!isCached(entity.getId())) {
        final String jsonString = this.serializer.serialize(entity, RestaurantEntity.class);
        this.executeAsynchronously(new CacheWriter(this.fileManager, entityFile, jsonString));
        setLastCacheUpdateTimeMillis();
      }
    }
  }

  @Override public boolean isCached(String id) {
    final File entityFile = this.buildFile(id);
    return this.fileManager.exists(entityFile);
  }

  @Override public boolean isExpired() {
    long currentTime = System.currentTimeMillis();
    long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

    boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

    if (expired) {
      this.evictAll();
    }

    return expired;
  }

  @Override public void evictAll() {
    this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
  }

  /**
   * Build a file, used to be inserted in the disk cache.
   *
   * @param id The id restaurant to build the file.
   * @return A valid file.
   */
  private File buildFile(String id) {
    final StringBuilder fileNameBuilder = new StringBuilder();
    fileNameBuilder.append(this.cacheDir.getPath());
    fileNameBuilder.append(File.separator);
    fileNameBuilder.append(DEFAULT_FILE_NAME);
    fileNameBuilder.append(id);

    return new File(fileNameBuilder.toString());
  }

  /**
   * Set in millis, the last time the cache was accessed.
   */
  private void setLastCacheUpdateTimeMillis() {
    final long currentMillis = System.currentTimeMillis();
    this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
  }

  /**
   * Get in millis, the last time the cache was accessed.
   */
  private long getLastCacheUpdateTimeMillis() {
    return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE);
  }

  /**
   * Executes a {@link Runnable} in another Thread.
   *
   * @param runnable {@link Runnable} to execute
   */
  private void executeAsynchronously(Runnable runnable) {
    this.threadExecutor.execute(runnable);
  }

  /**
   * {@link Runnable} class for writing to disk.
   */
  private static class CacheWriter implements Runnable {
    private final FileManager fileManager;
    private final File fileToWrite;
    private final String fileContent;

    CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
      this.fileManager = fileManager;
      this.fileToWrite = fileToWrite;
      this.fileContent = fileContent;
    }

    @Override public void run() {
      this.fileManager.writeToFile(fileToWrite, fileContent);
    }
  }

  /**
   * {@link Runnable} class for evicting all the cached files
   */
  private static class CacheEvictor implements Runnable {
    private final FileManager fileManager;
    private final File cacheDir;

    CacheEvictor(FileManager fileManager, File cacheDir) {
      this.fileManager = fileManager;
      this.cacheDir = cacheDir;
    }

    @Override public void run() {
      this.fileManager.clearDirectory(this.cacheDir);
    }
  }
}
