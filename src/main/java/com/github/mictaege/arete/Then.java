package com.github.mictaege.arete;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Test
public @interface Then {

    int seq() default 1;
    int step() default 1;
    /** short hand for step. */
    int value() default 1;

    String desc() default "";

}