package ru.practicum.shareit.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ItemsToUpdateDescriptionValidator implements ConstraintValidator<ValidItemsToUpdateDescription, String> {

    public void initialize(ValidItemsToUpdateDescription constraint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            return !(value.isEmpty() || value.isBlank());
        } else {
            return true;
        }

    }
}