package com.campusmanagement.annotation;


import com.campusmanagement.model.Reservatie;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndAfterStartValidator implements ConstraintValidator<EndAfterStart, Reservatie> {

    @Override
    public boolean isValid(Reservatie reservatie, ConstraintValidatorContext constraintValidatorContext) {
        if (reservatie.getStartTijdstip() == null || reservatie.getEindTijdstip() == null) {
            return true;  // Let @NotNull handle null values
        }
        return reservatie.getEindTijdstip().isAfter(reservatie.getStartTijdstip());
    }
}
