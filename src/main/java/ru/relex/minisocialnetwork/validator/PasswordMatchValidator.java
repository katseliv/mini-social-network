package ru.relex.minisocialnetwork.validator;

import ru.relex.minisocialnetwork.annotation.PasswordMatch;
import ru.relex.minisocialnetwork.model.dto.UserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserRegistrationDto> {

    @Override
    public boolean isValid(final UserRegistrationDto userRegistrationDto, final ConstraintValidatorContext context) {
        final String password = userRegistrationDto.getPassword();
        final String passwordConfirmation = userRegistrationDto.getPasswordConfirmation();
        return password != null && password.equals(passwordConfirmation);
    }

}
