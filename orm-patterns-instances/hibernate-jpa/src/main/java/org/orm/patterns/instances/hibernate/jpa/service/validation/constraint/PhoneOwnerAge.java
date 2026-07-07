package org.orm.patterns.instances.hibernate.jpa.service.validation.constraint;

import org.orm.patterns.instances.hibernate.jpa.service.validation.validator.PhoneOwnerAgeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * The interface Phone owner age.
 */
@Documented
@Constraint(validatedBy = PhoneOwnerAgeValidator.class)
@Target({
        ElementType.METHOD,
        ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneOwnerAge {

    /**
     * Value int.
     *
     * @return the int
     */
    int value() default Integer.MIN_VALUE;

    /**
     * Message string.
     *
     * @return the string
     */
    String message() default "Only a person is enough years old can own a phone";

    /**
     * Groups class [ ].
     *
     * @return the class [ ]
     */
    Class<?>[] groups() default {};

    /**
     * Payload class [ ].
     *
     * @return the class [ ]
     */
    Class<? extends Payload>[] payload() default {};
}
