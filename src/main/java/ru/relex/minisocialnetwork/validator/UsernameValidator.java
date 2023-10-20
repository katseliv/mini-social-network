package ru.relex.minisocialnetwork.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.relex.minisocialnetwork.annotation.UsernameUnique;
import ru.relex.minisocialnetwork.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UsernameValidator implements ConstraintValidator<UsernameUnique, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        final List<String> messages = new ArrayList<>();
        if (userRepository.existsByUsername(username)) {
            messages.add("Username exists.");
        }
        final String messageTemplate = String.join(", ", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return messages.size() == 0;
    }

}
