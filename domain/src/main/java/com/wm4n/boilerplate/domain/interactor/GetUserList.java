/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wm4n.boilerplate.domain.interactor;

import com.wm4n.boilerplate.domain.repository.UserRepository;
import com.wm4n.boilerplate.domain.User;
import com.wm4n.boilerplate.domain.executor.PostExecutionThread;
import com.wm4n.boilerplate.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link com.wm4n.boilerplate.domain.interactor.UseCase} that represents a use case for
 * retrieving a collection of all {@link com.wm4n.boilerplate.domain.User}.
 */
public class GetUserList extends UseCase<List<User>, Void> {

  private final UserRepository userRepository;

  @Inject
  GetUserList(UserRepository userRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.userRepository = userRepository;
  }

  @Override Observable<List<User>> buildUseCaseObservable(Void unused) {
    return this.userRepository.users();
  }
}
