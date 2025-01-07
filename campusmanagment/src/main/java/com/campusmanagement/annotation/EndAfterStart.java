package com.campusmanagement.annotation;

import jakarta.validation.Constraint;

@Constraint(validatedBy = EndAfterStartValidator.class)
public @interface EndAfterStart {
    String message() default "Eind tijdstip moet na het start tijdstip zijn";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
