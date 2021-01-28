package io.izzel.tools.func;

import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.SuppressWarnings;
import java.util.function.BiFunction;

public interface Func2<T1, T2, R> extends Func<R>, BiFunction<T1, T2, R> {
  R apply(T1 t1, T2 t2);

  @Override
  @SuppressWarnings("unchecked")
  default R applyArray(Object... args) {
    if (args.length < 2) {
      throw new IllegalArgumentException();
    }
    return apply((T1) args[0],(T2) args[1]);
  }
}
