package io.izzel.tools.func;

import java.lang.AssertionError;
import java.lang.Object;
import java.lang.Override;
import java.lang.Throwable;

public interface Func0<R> extends Func<R> {
  R apply0() throws Throwable;

  default R apply() {
    try {
      return apply0();
    } catch (Throwable t) {
      Func.throwException(t);
      throw new AssertionError();
    }
  }

  @Override
  default R applyArray(Object... args) {
    return apply();
  }
}
