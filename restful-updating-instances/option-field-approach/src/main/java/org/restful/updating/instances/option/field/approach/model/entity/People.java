package org.restful.updating.instances.option.field.approach.model.entity;

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
public class People implements Serializable {

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

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "age", nullable = false)
    Integer age;
}