package org.orm.patterns.instances.active.jdbc.model.entity;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.Table;
import org.orm.patterns.instances.active.jdbc.service.validation.PhoneValidator;

/**
 * The type Phone.
 */
@BelongsTo(
        parent = Person.class,
        foreignKeyName = "person_id"
)
@Table("phone")
public class Phone extends Model {

    static {
        validatePresenceOf("person_id")
                .message("The Person id is missing");

        validateRegexpOf("number", "^(\\+\\d{1}( )?){1}((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")
                .message("The Phone number is missing");

        validateWith(new PhoneValidator()).message("Only a person over 14 years old can own a phone");
    }
}