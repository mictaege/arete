package com.github.mictaege.arete;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Nested
@ClassTemplate
@ExtendWith(VariableJourneyExtension.class)
@TestClassOrder(AreteOrderer.class)
@TestMethodOrder(AreteOrderer.class)
@DisplayNameGeneration(VariableJourneyNameGenerator.class)
@TestInstance(PER_CLASS)
public @interface VariableJourney {

    int order() default 1;
    /** short hand for order. */
    int value() default 1;

    String desc() default "";
    
}