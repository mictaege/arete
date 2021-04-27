package com.github.mictaege.arete;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Nested
@TestMethodOrder(DescribeOrderer.class)
@DisplayNameGeneration(DescribeNameGenerator.class)
@TestInstance(PER_METHOD)
public @interface Describe {

    String desc() default "";

}