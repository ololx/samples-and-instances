package org.orm.patterns.instances.hibernate.jpa.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * The type Person.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        of = {
                "id"
        },
        doNotUseGetters = true
)
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "Person")
@Table(name = "person")
public class Person {

    @JsonProperty("id")
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            insertable = false,
            nullable = false
    )
    Long id;

    @JsonProperty("first_name")
    @Column(name = "first_name", nullable = false)
    String firstName;

    @JsonProperty("last_name")
    @Column(name = "last_name", nullable = false)
    String lastName;

    @JsonProperty("age")
    @Column(name = "age", nullable = false)
    Integer age;
}