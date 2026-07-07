package org.orm.patterns.instances.hibernate.entity.manager.service.validation.validator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.orm.patterns.instances.hibernate.entity.manager.model.entity.Person;
import org.orm.patterns.instances.hibernate.entity.manager.service.validation.constraint.PhoneOwnerAge;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The type Phone owner age validator.
 */
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
public class PhoneOwnerAgeValidator implements ConstraintValidator<PhoneOwnerAge, Person> {

    /**
     * The Age.
     */
    @NonFinal private int age;

    /**
     * Initialize.
     *
     * @param constraintAnnotation the constraint annotation
     */
    @Override
    public void initialize(PhoneOwnerAge constraintAnnotation) {
        this.age = constraintAnnotation.value();
    }

    /**
     * Is valid boolean.
     *
     * @param person                     the person
     * @param constraintValidatorContext the constraint validator context
     * @return the boolean
     */
    @Override
    public boolean isValid(Person person, ConstraintValidatorContext constraintValidatorContext) {
        if (person == null) return true;

        return isValidOwnerAge(person);
    }

    /**
     * Is valid owner age boolean.
     *
     * @param person the person
     * @return the boolean
     */
    public boolean isValidOwnerAge(Person person) {
        return this.age <= person.getAge();
    }
}
