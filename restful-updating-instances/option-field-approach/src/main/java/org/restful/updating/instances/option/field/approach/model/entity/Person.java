package org.restful.updating.instances.option.field.approach.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @project restful-updating-instances
 * @created 28.06.2020 07:54
 * <p>
 * @author Alexander A. Kropotin
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        of = {
                "uid"
        },
        doNotUseGetters = true
)
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "People")
@Table(name = "people")
public class Person implements Serializable {

    @JsonProperty("uid")
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "people_pkey"
    )
    @SequenceGenerator(
            name = "people_pkey",
            sequenceName = "people_uid_seq",
            allocationSize = 1
    )
    @Column(
            name = "uid",
            insertable = false,
            nullable = false
    )
    Long uid;

    @JsonProperty("first_name")
    @Column(
            name = "first_name",
            nullable = false
    )
    String firstName;

    @JsonProperty("last_name")
    @Column(
            name = "last_name",
            nullable = false
    )
    String lastName;

    @JsonProperty("age")
    @Column(
            name = "age",
            nullable = false
    )
    Integer age;
}