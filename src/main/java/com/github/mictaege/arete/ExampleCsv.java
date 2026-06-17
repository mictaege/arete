package com.github.mictaege.arete;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ExtendWith(ExampleCsvCtxProvider.class)
@TestTemplate
public @interface ExampleCsv {

    int order() default 1;
    /** short hand for order. */
    int value() default Order.DEFAULT;

    String desc() default "";

    String[] columns();

    char delimiter() default ',';

    String csvData() default "";

    String csvResourcePath() default "";

    Class<? extends ExampleCsvSource> srcClass() default ExampleCsvSource.class;

    String srcMethod() default "";

}
