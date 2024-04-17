package com.michael.blog_mvc.validation;

import com.michael.blog_mvc.payload.request.RegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof RegistrationRequest) {
            RegistrationRequest registrationRequest = (RegistrationRequest) obj;
            return registrationRequest.getPassword().equals(registrationRequest.getMatchingPassword());
        } else
            return false;
    }
}