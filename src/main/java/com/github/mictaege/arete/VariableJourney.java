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
@TestMethodOrder(VariableJourneyOrderer.class)
@DisplayNameGeneration(VariableJourneyNameGenerator.class)
@TestInstance(PER_CLASS)
public @interface VariableJourney {

    int value() default 1;

    String desc() default "";
    
}