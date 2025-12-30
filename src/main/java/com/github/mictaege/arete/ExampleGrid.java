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
@ExtendWith(ExampleGridCtxProvider.class)
@TestTemplate
public @interface ExampleGrid {

    int order() default Order.DEFAULT;

    String desc() default "";

    String[] columns();

    Class<? extends ExampleGridSource> srcClass() default ExampleGridSource.class;

    String srcMethod() default "";

}
