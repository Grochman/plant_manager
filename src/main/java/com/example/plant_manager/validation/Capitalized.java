package com.example.plant_manager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CapitalizedValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Capitalized {
    String message() default "Must start from capital letter";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}