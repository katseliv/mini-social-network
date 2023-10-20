package ru.relex.minisocialnetwork.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.relex.minisocialnetwork.annotation.EmailUnique;
import ru.relex.minisocialnetwork.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailUnique, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext context) {
        final List<String> messages = new ArrayList<>();
        if (userRepository.existsByEmail(email)) {
            messages.add("Email exists.");
        }
        final String messageTemplate = String.join(", ", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return messages.size() == 0;
    }

}
