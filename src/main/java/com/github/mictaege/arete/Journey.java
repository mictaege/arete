package com.github.mictaege.arete;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.lang.annotation.*;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Nested
@TestMethodOrder(JourneyOrderer.class)
@DisplayNameGeneration(JourneyNameGenerator.class)
@TestInstance(PER_CLASS)
public @interface Journey {

    int value() default 1;

    String desc() default "";
    
}