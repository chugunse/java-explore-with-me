package ru.practicum.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CastomDataTimeValidator.class)
public @interface CastomDataTime {
    String message() default "дата и время не удовлетворяют условию";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int delay();
}
