package org.orm.patterns.instances.active.jdbc.service.validation;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.validation.ValidatorAdapter;
import org.orm.patterns.instances.active.jdbc.model.entity.Person;

/**
 * The type Phone validator.
 */
public class PhoneValidator extends ValidatorAdapter {

    @Override
    public void validate(Model model) {
        boolean valid = true;
        valid = validatePersonAge(model);

        if(!valid) model.addValidator(this, "phone_owner_validation");

        return;
    }

    private boolean validatePersonAge(Model model) {
        Person person = null;
        if ((person = model.parent(Person.class)) != null && person.getInteger("age") < 14) return false;

        return true;
    }
}
