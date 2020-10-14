package org.orm.patterns.instances.active.jdbc.model.entity;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 * The type Person.
 */
@Table("person")
public class Person extends Model {

    static {
        validatePresenceOf("first_name", "last_name")
                .message("The Person name is missing");

        validateRange("age", 0, 101)
                .message("The Person agу is amazing");

        validateNumericalityOf("age")
                .allowNull(false)
                .message("The Person age is amazing");
    }
}