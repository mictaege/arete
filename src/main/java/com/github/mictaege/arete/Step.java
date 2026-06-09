package com.github.mictaege.arete;

import org.junit.jupiter.api.Test;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Test
public @interface Step {

    int value() default 1;

    String desc() default "";

    String[] variant() default "";
}