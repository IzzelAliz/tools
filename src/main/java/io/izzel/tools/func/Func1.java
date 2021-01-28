package io.izzel.tools.func;

import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.SuppressWarnings;
import java.util.function.Function;

public interface Func1<T1, R> extends Func<R>, Function<T1, R> {
  R apply(T1 t1);

  @Override
  @SuppressWarnings("unchecked")
  default R applyArray(Object... args) {
    if (args.length < 1) {
      throw new IllegalArgumentException();
    }
    return apply((T1) args[0]);
  }
}
