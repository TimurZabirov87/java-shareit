package ru.practicum.shareit.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ItemsNameValidator.class)
@Documented
public @interface ValidItemsName {

    String message() default "Name is invalid: cannot be empty or blank";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
