package com.example.plant_manager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CapitalizedValidator implements ConstraintValidator<Capitalized, String> {

    @Override
    public void initialize(Capitalized constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return Character.isUpperCase(value.charAt(0));
    }
}