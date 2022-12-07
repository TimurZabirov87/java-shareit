package ru.practicum.shareit.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ItemsToUpdateNameValidator implements ConstraintValidator<ValidItemsToUpdateName, String> {

    public void initialize(ValidItemsToUpdateName constraint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            return !(value.isEmpty() || value.isBlank());
        } else {
            return true;
        }
    }
}