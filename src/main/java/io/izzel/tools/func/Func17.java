package io.izzel.tools.func;

import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.SuppressWarnings;

public interface Func17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R> extends Func<R> {
  R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12,
      T13 t13, T14 t14, T15 t15, T16 t16, T17 t17);

  @Override
  @SuppressWarnings("unchecked")
  default R applyArray(Object... args) {
    if (args.length < 17) {
      throw new IllegalArgumentException();
    }
    return apply((T1) args[0],(T2) args[1],(T3) args[2],(T4) args[3],(T5) args[4],(T6) args[5],(T7) args[6],(T8) args[7],(T9) args[8],(T10) args[9],(T11) args[10],(T12) args[11],(T13) args[12],(T14) args[13],(T15) args[14],(T16) args[15],(T17) args[16]);
  }
}