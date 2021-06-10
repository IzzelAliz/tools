package io.izzel.tools;

import io.izzel.tools.func.Func;
import io.izzel.tools.func.Func0;
import io.izzel.tools.product.Product;

public enum Unit implements Product {
    INSTANCE;

    @Override
    public String toString() {
        return "Unit";
    }

    @Override
    public <R> R map(Func<R> func) {
        if (func instanceof Func0) {
            return ((Func0<R>) func).apply();
        } else {
            return func.applyArray();
        }
    }

    @Override
    public Object productElement(int i) throws IndexOutOfBoundsException {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int productArity() {
        return 0;
    }
}
