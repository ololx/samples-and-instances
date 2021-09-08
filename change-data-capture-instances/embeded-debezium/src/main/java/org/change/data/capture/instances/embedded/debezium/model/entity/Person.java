package org.change.data.capture.instances.embedded.debezium.model.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Person.
 *
 * @author Alexander A. Kropotin
 * @project embeded -debezium
 * @created 2021 -09-08 14:51 <p>
 */
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "Person")
@Table(name = "person")
public final class Person {

    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "fullname")
    String fullname;
}
