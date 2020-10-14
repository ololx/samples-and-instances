package org.orm.patterns.instances.active.jdbc.model.entity;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.Table;

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
        validateRegexpOf("number", "^(\\+\\d{1}( )?){1}((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$").message("123");
    }
}