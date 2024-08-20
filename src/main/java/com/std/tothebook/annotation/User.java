package com.std.tothebook.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({PARAMETER, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface User {
}
