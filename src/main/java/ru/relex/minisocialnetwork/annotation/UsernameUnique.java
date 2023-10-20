package ru.relex.minisocialnetwork.annotation;

import ru.relex.minisocialnetwork.validator.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface UsernameUnique {

    String message() default "Such username already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
