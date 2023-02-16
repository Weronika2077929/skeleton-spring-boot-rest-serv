package com.firstClientServer.firstApp.server.music.handler;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsValidator.class)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists_wl {
    String message() default "doesnt.exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}