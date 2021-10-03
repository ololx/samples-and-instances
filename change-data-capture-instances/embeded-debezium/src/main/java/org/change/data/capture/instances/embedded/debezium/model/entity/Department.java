package org.change.data.capture.instances.embedded.debezium.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Entity(name = "Department")
@Table(name = "department")
public final class Department {

    @JsonProperty("department_id")
    @Id
    @Column(name = "department_id")
    Long departmentId;

    @JsonProperty("code")
    @Column(name = "code")
    String code;
}
