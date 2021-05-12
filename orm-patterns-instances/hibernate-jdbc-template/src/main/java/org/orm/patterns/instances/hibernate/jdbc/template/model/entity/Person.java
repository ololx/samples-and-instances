package org.orm.patterns.instances.hibernate.jdbc.template.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator="person_pkey_pkey"
    )
    @SequenceGenerator(name="person_pkey_pkey",
            sequenceName="person_id_seq",
            allocationSize=1
    )
    @Column(
            name = "id",
            insertable = false,
            nullable = false
    )
    Long id;

    @JsonProperty("first_name")
    @NotNull(
            message = "The Person name is missing"
    )
    @Column(
            name = "first_name",
            nullable = false
    )
    String firstName;

    @JsonProperty("last_name")
    @NotNull(
            message = "The Person name is missing"
    )
    @Column(
            name = "last_name",
            nullable = false
    )
    String lastName;

    @JsonProperty("age")
    @Range(
            min = 0,
            max = 101,
            message = "The Person age is amazing"
    )
    @Column(
            name = "age",
            nullable = false
    )
    Integer age;

    @Transient
    List<Phone> phones;
}