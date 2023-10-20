package ru.relex.minisocialnetwork.annotation;

import ru.relex.minisocialnetwork.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailUnique {

    String message() default "Such email already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
