package io.izzel.tools.generator;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ProductsGenerator {

    private static final String PACKAGE = "io.izzel.tools.product";
    private static final ClassName PRODUCT_TYPE = ClassName.get(PACKAGE, "Product");

    public static void run() throws IOException {
        writeProduct();
        for (int i = 1; i <= 22; i++) {
            writeProduct(i);
        }
    }

    private static void writeProduct() throws IOException {
        JavaFile.builder(PACKAGE, TypeSpec.interfaceBuilder(PRODUCT_TYPE)
            .addModifiers(Modifier.PUBLIC)
            .addMethod(MethodSpec.methodBuilder("map")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(ParameterSpec.builder(ParameterizedTypeName.get(FunctionsGenerator.FUNC_TYPE, FunctionsGenerator.R_TYPE), "func").build())
                .addTypeVariable(FunctionsGenerator.R_TYPE)
                .returns(FunctionsGenerator.R_TYPE)
                .build())
            .addMethod(MethodSpec.methodBuilder("productElement")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addParameter(TypeName.INT, "i")
                .returns(TypeName.OBJECT)
                .addException(ClassName.get(IndexOutOfBoundsException.class))
                .build())
            .addMethod(MethodSpec.methodBuilder("productArity")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .returns(TypeName.INT)
                .build())
            .addMethod(MethodSpec.methodBuilder("toList")
                .addModifiers(Modifier.PUBLIC, Modifier.DEFAULT)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), WildcardTypeName.subtypeOf(TypeName.OBJECT)))
                .addStatement("return map($T::$N)", Arrays.class, "asList")
                .build())
            .addMethods(IntStream.rangeClosed(1, 22).mapToObj(n ->
                MethodSpec.methodBuilder("of")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(ParameterizedTypeName.get(productTypeOf(n), IntStream.rangeClosed(1, n).mapToObj(x ->
                        TypeVariableName.get("T" + x)).toArray(TypeName[]::new)))
                    .addTypeVariables(IntStream.rangeClosed(1, n).mapToObj(x ->
                        TypeVariableName.get("T" + x)).collect(Collectors.toList()))
                    .addParameters(IntStream.rangeClosed(1, n).mapToObj(x ->
                        ParameterSpec.builder(TypeVariableName.get("T" + x), "t" + x).build()).collect(Collectors.toList()))
                    .addStatement("return new $T<>($L)", productTypeOf(n), IntStream.rangeClosed(1, n).mapToObj(x ->
                        "t" + x).collect(Collectors.joining(",")))
                    .build()).collect(Collectors.toList()))
            .addMethod(switchConstructor(MethodSpec.methodBuilder("fromArray")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .varargs()
                .returns(PRODUCT_TYPE)
                .addParameter(ArrayTypeName.of(TypeName.OBJECT), "array")
                .beginControlFlow("switch (array.length)"))
                .endControlFlow()
                .addStatement("throw new $T()", IndexOutOfBoundsException.class)
                .build())
            .build())
            .build().writeTo(Paths.get("./src/main/java"));
    }

    private static void writeProduct(int i) throws IOException {
        JavaFile.builder(PACKAGE, TypeSpec.classBuilder(ClassName.get(PACKAGE, "Product" + i))
            .addModifiers(Modifier.PUBLIC)
            .addTypeVariables(IntStream.rangeClosed(1, i).mapToObj(n -> TypeVariableName.get("T" + n)).collect(Collectors.toList()))
            .addSuperinterface(PRODUCT_TYPE)
            .addFields(IntStream.rangeClosed(1, i).mapToObj(n ->
                FieldSpec.builder(TypeVariableName.get("T" + n), "_" + n, Modifier.PUBLIC, Modifier.FINAL).build()).collect(Collectors.toList())
            )
            .addMethod(assignFields(MethodSpec.constructorBuilder()
                .addParameters(IntStream.rangeClosed(1, i).mapToObj(n ->
                    ParameterSpec.builder(TypeVariableName.get("T" + n), "t" + n).build()).collect(Collectors.toList())), i)
                .build())
            .addMethod(MethodSpec.methodBuilder("map")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(allVarArgs(i), "func").build())
                .addTypeVariable(FunctionsGenerator.R_TYPE)
                .returns(FunctionsGenerator.R_TYPE)
                .addStatement("return func.apply($L)",
                    IntStream.rangeClosed(1, i).mapToObj(n -> "_" + n).collect(Collectors.joining(",")))
                .build())
            .addMethod(MethodSpec.methodBuilder("map")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ParameterizedTypeName.get(FunctionsGenerator.FUNC_TYPE, FunctionsGenerator.R_TYPE), "func").build())
                .addTypeVariable(FunctionsGenerator.R_TYPE)
                .returns(FunctionsGenerator.R_TYPE)
                .beginControlFlow("if (func instanceof $T)", FunctionsGenerator.funcTypeOf(i))
                .addStatement("return (($T) func).apply($L)", allVarArgs(i),
                    IntStream.rangeClosed(1, i).mapToObj(n -> "_" + n).collect(Collectors.joining(",")))
                .endControlFlow()
                .addStatement("return func.applyArray($L)", IntStream.rangeClosed(1, i).mapToObj(n -> "_" + n).collect(Collectors.joining(",")))
                .build())
            .addMethod(switchTable(MethodSpec.methodBuilder("productElement")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.INT, "i")
                .returns(TypeName.OBJECT)
                .beginControlFlow("switch ($N)", "i"), i)
                .endControlFlow()
                .addStatement("throw new $T($S + i + $S)", IndexOutOfBoundsException.class, "Index: ", ", Max: " + i)
                .addException(ClassName.get(IndexOutOfBoundsException.class))
                .build())
            .addMethod(MethodSpec.methodBuilder("productArity")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.INT)
                .addStatement("return $L", i)
                .build())
            .addMethod(MethodSpec.methodBuilder("toString")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(String.class))
                .addStatement("return $S+$L+$S", "Product" + i + "[", IntStream.rangeClosed(1, i).mapToObj(n ->
                    "_" + n).collect(Collectors.joining("+\",\"+")), "]")
                .build())
            .addMethod(MethodSpec.methodBuilder("equals")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.BOOLEAN)
                .addParameter(TypeName.OBJECT, "that")
                .addStatement("if (this == that) return true")
                .addStatement("if (that == null || this.getClass() != that.getClass()) return false")
                .addStatement("$T p = ($T) that", wildcardTypeOf(i), wildcardTypeOf(i))
                .addStatement("return " + IntStream.rangeClosed(1, i).mapToObj(n -> "$1T.equals(_" + n + ", p._" + n + ")").collect(Collectors.joining(" && ")),
                    Objects.class)
                .build())
            .addMethod(MethodSpec.methodBuilder("hashCode")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.INT)
                .addStatement("return $T.hash($L)", Objects.class, IntStream.rangeClosed(1, i).mapToObj(n -> "_" + n).collect(Collectors.joining(",")))
                .build())
            .build()
        ).build().writeTo(Paths.get("./src/main/java"));
    }

    private static MethodSpec.Builder switchConstructor(MethodSpec.Builder builder) {
        for (int i = 1; i <= 22; i++) {
            builder.addStatement("case $L: return new $T<>($L)", i, productTypeOf(i),
                IntStream.range(0, i).mapToObj(n -> "array[" + n + "]").collect(Collectors.joining(",")));
        }
        return builder;
    }

    private static MethodSpec.Builder switchTable(MethodSpec.Builder builder, int n) {
        for (int i = 0; i < n; i++) {
            builder.addStatement("case $L: return $N", i, "_" + (i + 1));
        }
        return builder;
    }

    private static MethodSpec.Builder assignFields(MethodSpec.Builder builder, int n) {
        IntStream.rangeClosed(1, n).forEach(i -> builder
            .addStatement("this.$N = $N", "_" + i, "t" + i));
        return builder;
    }

    private static ParameterizedTypeName allVarArgs(int i) {
        return ParameterizedTypeName.get(FunctionsGenerator.funcTypeOf(i),
            Stream.concat(IntStream.rangeClosed(1, i).mapToObj(n -> TypeVariableName.get("T" + n)), Stream.of(FunctionsGenerator.R_TYPE)).toArray(TypeName[]::new));
    }

    private static ClassName productTypeOf(int i) {
        return ClassName.get(PACKAGE, "Product" + i);
    }

    private static ParameterizedTypeName wildcardTypeOf(int i) {
        return ParameterizedTypeName.get(productTypeOf(i), IntStream.rangeClosed(1, i).mapToObj(n ->
            WildcardTypeName.subtypeOf(TypeName.OBJECT)).toArray(TypeName[]::new));
    }
}
