package com.github.mictaege.arete;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Links a specification or any nested part (feature, description, scenario, journey)
 * to an external reference, e.g. a requirement, user story or issue of a tracking system.
 *
 * <p>References declared on a container (spec, feature, description) also apply to all
 * nested parts, so a reference on a spec covers all its scenarios and journeys.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Refs.class)
@Documented
public @interface Ref {

    /** The reference id, e.g. "PROJ-123". Defaults to empty so composed annotations like {@code @GithubRef} can be meta-annotated with {@code @Ref}. */
    String value() default "";

    /** An optional URL prefix of the reference. The {@code value} is appended to it, unless the url contains a {@code {0}} placeholder which is replaced by the value. */
    String url() default "";

}
