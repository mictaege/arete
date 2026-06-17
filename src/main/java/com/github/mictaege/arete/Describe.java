package com.github.mictaege.arete;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Nested
@TestClassOrder(AreteOrderer.class)
@TestMethodOrder(AreteOrderer.class)
@DisplayNameGeneration(DescribeNameGenerator.class)
@TestInstance(PER_METHOD)
public @interface Describe {

    int order() default 1;
    /** short hand for order. */
    int value() default 2;

    String desc() default "";

}