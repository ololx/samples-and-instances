package io.github.innopolis.university.java.team.di.realization.examples;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * project di-realization-examples
 * created 15.02.2022 15:21
 *
 * @author Alexander A. Kropotin
 */
@BindingAnnotation
@Target({FIELD, PARAMETER, METHOD})
@Retention(RUNTIME)
public @interface Red {
}
