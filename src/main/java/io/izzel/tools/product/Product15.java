package io.izzel.tools.product;

import io.izzel.tools.func.Func;
import io.izzel.tools.func.Func15;
import java.lang.IndexOutOfBoundsException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

public class Product15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> implements Product {
  public final T1 _1;

  public final T2 _2;

  public final T3 _3;

  public final T4 _4;

  public final T5 _5;

  public final T6 _6;

  public final T7 _7;

  public final T8 _8;

  public final T9 _9;

  public final T10 _10;

  public final T11 _11;

  public final T12 _12;

  public final T13 _13;

  public final T14 _14;

  public final T15 _15;

  Product15(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11,
      T12 t12, T13 t13, T14 t14, T15 t15) {
    this._1 = t1;
    this._2 = t2;
    this._3 = t3;
    this._4 = t4;
    this._5 = t5;
    this._6 = t6;
    this._7 = t7;
    this._8 = t8;
    this._9 = t9;
    this._10 = t10;
    this._11 = t11;
    this._12 = t12;
    this._13 = t13;
    this._14 = t14;
    this._15 = t15;
  }

  public <R> R map(
      Func15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> func) {
    return func.apply(_1,_2,_3,_4,_5,_6,_7,_8,_9,_10,_11,_12,_13,_14,_15);
  }

  @Override
  public <R> R map(Func<R> func) {
    if (func instanceof Func15) {
      return ((Func15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R>) func).apply(_1,_2,_3,_4,_5,_6,_7,_8,_9,_10,_11,_12,_13,_14,_15);
    }
    return func.applyArray(_1,_2,_3,_4,_5,_6,_7,_8,_9,_10,_11,_12,_13,_14,_15);
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
      case 7: return _8;
      case 8: return _9;
      case 9: return _10;
      case 10: return _11;
      case 11: return _12;
      case 12: return _13;
      case 13: return _14;
      case 14: return _15;
    }
    throw new IndexOutOfBoundsException("Index: " + i + ", Max: 15");
  }

  @Override
  public int productArity() {
    return 15;
  }

  @Override
  public String toString() {
    return "Product15["+_1+","+_2+","+_3+","+_4+","+_5+","+_6+","+_7+","+_8+","+_9+","+_10+","+_11+","+_12+","+_13+","+_14+","+_15+"]";
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) return true;
    if (that == null || this.getClass() != that.getClass()) return false;
    Product15<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> p = (Product15<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) that;
    return Objects.equals(_1, p._1) && Objects.equals(_2, p._2) && Objects.equals(_3, p._3) && Objects.equals(_4, p._4) && Objects.equals(_5, p._5) && Objects.equals(_6, p._6) && Objects.equals(_7, p._7) && Objects.equals(_8, p._8) && Objects.equals(_9, p._9) && Objects.equals(_10, p._10) && Objects.equals(_11, p._11) && Objects.equals(_12, p._12) && Objects.equals(_13, p._13) && Objects.equals(_14, p._14) && Objects.equals(_15, p._15);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_1,_2,_3,_4,_5,_6,_7,_8,_9,_10,_11,_12,_13,_14,_15);
  }
}
