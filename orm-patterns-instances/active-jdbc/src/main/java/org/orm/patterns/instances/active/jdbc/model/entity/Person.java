package org.orm.patterns.instances.active.jdbc.model.entity;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("person")
public class Person extends Model {

    {
        this.set("id", null);
        this.set("first_name", null);
        this.set("last_name", null);
        this.set("age", null);
    }
}