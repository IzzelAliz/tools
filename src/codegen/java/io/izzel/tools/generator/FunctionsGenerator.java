package io.izzel.tools.generator;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FunctionsGenerator {

    private static final String PACKAGE = "io.izzel.tools.func";
    static final ClassName FUNC_TYPE = ClassName.get(PACKAGE, "Func");
    static final TypeVariableName R_TYPE = TypeVariableName.get("R");

    public static void run() throws IOException {
        for (int i = 0; i <= 22; i++) {
            write(i);
        }
    }

    private static void write(int i) throws IOException {
        MethodSpec.Builder applyArrayBuilder = MethodSpec.methodBuilder("applyArray")
            .addAnnotation(Override.class)
            .addParameter(ArrayTypeName.of(TypeName.OBJECT), "args").varargs()
            .addModifiers(Modifier.PUBLIC, Modifier.DEFAULT);
        if (i != 0) {
            applyArrayBuilder.addAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "$S", "unchecked").build())
                .beginControlFlow("if (args.length < $L)", i)
                .addStatement("throw new $T()", IllegalArgumentException.class)
                .endControlFlow();
        }
        applyArrayBuilder.addStatement("return apply(" + IntStream.range(0, i)
            .mapToObj(n -> "(T" + (n + 1) + ") args[" + n + "]").collect(Collectors.joining(",")) + ")")
            .returns(TypeVariableName.get("R"));
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder("Func" + i)
            .addModifiers(Modifier.PUBLIC)
            .addTypeVariables(
                Stream.concat(
                    IntStream.rangeClosed(1, i).mapToObj(n -> TypeVariableName.get("T" + n)),
                    Stream.of(R_TYPE)
                ).collect(Collectors.toList())
            )
            .addSuperinterface(ParameterizedTypeName.get(FUNC_TYPE, R_TYPE))
            .addMethod(
                MethodSpec.methodBuilder("apply")
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                    .addParameters(
                        IntStream.rangeClosed(1, i)
                            .mapToObj(n -> ParameterSpec.builder(TypeVariableName.get("T" + n), "t" + n).build())
                            .collect(Collectors.toList())
                    ).returns(R_TYPE).build()
            )
            .addMethod(
                applyArrayBuilder.build()
            );
        if (i == 1) {
            builder.addSuperinterface(ParameterizedTypeName.get(ClassName.get(Function.class), TypeVariableName.get("T1"), R_TYPE));
        } else if (i == 2) {
            builder.addSuperinterface(ParameterizedTypeName.get(ClassName.get(BiFunction.class), TypeVariableName.get("T1"), TypeVariableName.get("T2"), R_TYPE));
        }

        JavaFile.builder(PACKAGE, builder.build()).build()
            .writeTo(Paths.get("./src/main/java"));
    }

    static ClassName funcTypeOf(int i) {
        return ClassName.get(PACKAGE, "Func" + i);
    }
}
