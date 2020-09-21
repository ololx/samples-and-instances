package org.restful.data.storing.instances.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.io.Serializable;

@Table("person")
public class Person extends Model {

    {
        this.set("id", null);
        this.set("first_name", null);
        this.set("last_name", null);
        this.set("age", null);
    }
}