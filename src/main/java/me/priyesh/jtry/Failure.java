/*
 * Copyright (c) Priyesh Patel 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.priyesh.jtry;

import me.priyesh.jtry.functions.Function1;
import me.priyesh.jtry.functions.UFunction1;
import me.priyesh.jtry.functions.Supplier;

public final class Failure<A> extends Try<A> {

  private final Throwable throwable;

  Failure(Throwable throwable) {
    super(null);
    this.throwable = throwable;
  }

  public final boolean isFailure() {
    return true;
  }

  public final boolean isSuccess() {
    return false;
  }

  public final A getOrElse(Supplier<A> defaultValue) {
    return defaultValue.get();
  }

  public Try<A> orElse(Supplier<Try<A>> defaultValue) {
    return defaultValue.get();
  }

  public final A get() throws RuntimeException {
    throw throwable instanceof RuntimeException
        ? (RuntimeException) throwable
        : new RuntimeException(throwable);
  }

  public final <B> Try<B> flatMap(UFunction1<A, Try<B>> f) {
    return new Failure<>(throwable);
  }

  public final <B> Try<B> map(Function1<A, B> f) {
    return new Failure<>(throwable);
  }

  public <B> void foreach(Function1<A, B> f) {}

  public Try<A> recover(UFunction1<Throwable, A> f) {
    try {
      return new Success<>(f.apply(throwable));
    } catch (Throwable throwable) {
      return new Failure<>(throwable);
    }
  }
}