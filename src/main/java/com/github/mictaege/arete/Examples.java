package com.github.mictaege.arete;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ExtendWith(ExampleCtxProvider.class)
@TestTemplate
public @interface Examples {

    String pattern();

    Class<? extends ExampleSource> srcClass() default ExampleSource.class;
    String srcMethod() default "";

    int order() default 2;

}
