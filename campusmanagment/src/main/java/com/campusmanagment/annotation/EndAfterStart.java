package com.campusmanagment.annotation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EndAfterStartValidator.class)
public @interface EndAfterStart {
    String message() default "Eind tijdstip moet na het start tijdstip zijn";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
