package io.izzel.tools.product;

import io.izzel.tools.func.Func;
import io.izzel.tools.func.Func7;
import java.lang.IndexOutOfBoundsException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

public class Product7<T1, T2, T3, T4, T5, T6, T7> implements Product {
  public final T1 _1;

  public final T2 _2;

  public final T3 _3;

  public final T4 _4;

  public final T5 _5;

  public final T6 _6;

  public final T7 _7;

  Product7(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
    this._1 = t1;
    this._2 = t2;
    this._3 = t3;
    this._4 = t4;
    this._5 = t5;
    this._6 = t6;
    this._7 = t7;
  }

  public <R> R map(Func7<T1, T2, T3, T4, T5, T6, T7, R> func) {
    return func.apply(_1,_2,_3,_4,_5,_6,_7);
  }

  @Override
  public <R> R map(Func<R> func) {
    if (func instanceof Func7) {
      return ((Func7<T1, T2, T3, T4, T5, T6, T7, R>) func).apply(_1,_2,_3,_4,_5,_6,_7);
    }
    return func.applyArray(_1,_2,_3,_4,_5,_6,_7);
  }

  @Override
  public Object productElement(int i) throws IndexOutOfBoundsException {
    switch (i) {
      case 0: return _1;
      case 1: return _2;
      case 2: return _3;
      case 3: return _4;
      case 4: return _5;
      case 5: return _6;
      case 6: return _7;
    }
    throw new IndexOutOfBoundsException("Index: " + i + ", Max: 7");
  }

  @Override
  public int productArity() {
    return 7;
  }

  @Override
  public String toString() {
    return "Product7["+_1+","+_2+","+_3+","+_4+","+_5+","+_6+","+_7+"]";
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) return true;
    if (that == null || this.getClass() != that.getClass()) return false;
    Product7<?, ?, ?, ?, ?, ?, ?> p = (Product7<?, ?, ?, ?, ?, ?, ?>) that;
    return Objects.equals(_1, p._1) && Objects.equals(_2, p._2) && Objects.equals(_3, p._3) && Objects.equals(_4, p._4) && Objects.equals(_5, p._5) && Objects.equals(_6, p._6) && Objects.equals(_7, p._7);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_1,_2,_3,_4,_5,_6,_7);
  }
}
