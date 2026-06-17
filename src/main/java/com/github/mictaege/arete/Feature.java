package com.github.mictaege.arete;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

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
@DisplayNameGeneration(FeatureNameGenerator.class)
@TestInstance(PER_CLASS)
public @interface Feature {

    int order() default 1;
    /** short hand for order. */
    int value() default 1;

    String desc() default "";

}