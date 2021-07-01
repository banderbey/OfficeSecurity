package com.example.officesecurity.userInterface;

import com.example.officesecurity.validator.UserExcessValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = UserExcessValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExcessConstraint {
    String message() default "Invalid userName";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
