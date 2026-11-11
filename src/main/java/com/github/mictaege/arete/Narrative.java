package com.github.mictaege.arete;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ExtendWith(NarrativeContextExtension.class)
public @interface Narrative {

    String header() default "Narrative:";

    String[] value();

    String[] imageResourcePath() default {};

    String[] plantUml() default {};

    String[] attachmentResourcePath() default {};

}
