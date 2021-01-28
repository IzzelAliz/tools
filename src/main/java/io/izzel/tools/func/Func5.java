package io.izzel.tools.func;

import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.SuppressWarnings;

public interface Func5<T1, T2, T3, T4, T5, R> extends Func<R> {
  R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);

  @Override
  @SuppressWarnings("unchecked")
  default R applyArray(Object... args) {
    if (args.length < 5) {
      throw new IllegalArgumentException();
    }
    return apply((T1) args[0],(T2) args[1],(T3) args[2],(T4) args[3],(T5) args[4]);
  }
}