package ru.practicum.util.validator;

import util.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class CastomDataTimeValidator implements ConstraintValidator<CastomDataTime, Object> {
    public int delay;

    @Override
    public void initialize(final CastomDataTime constraintAnnotation) {
        this.delay = constraintAnnotation.delay();
    }

    @Override
    public boolean isValid(Object date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }
        return LocalDateTime.parse((String) date, Constants.formatter)
                .isAfter(LocalDateTime.now().plusHours(delay));
    }
}
