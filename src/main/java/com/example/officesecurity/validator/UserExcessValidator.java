package com.example.officesecurity.validator;

import com.example.officesecurity.userInterface.UserExcessConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExcessValidator implements
        ConstraintValidator<UserExcessConstraint, String> {

    @Override
    public void initialize(UserExcessConstraint constantNumber) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
        return contactField != null && contactField.matches("[a-zA-Z]+")
                && (contactField.length() > 3);
    }

}