package io.izzel.tools.func;

import java.lang.Object;
import java.lang.Override;

public interface Func0<R> extends Func<R> {
  R apply();

  @Override
  default R applyArray(Object... args) {
    return apply();
  }
}
