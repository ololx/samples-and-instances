package org.some.api.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * The type Some data.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 2021 -07-06 17:13 <p>
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
@Entity(name = "SomeData")
@Table(name = "some_data")
public final class SomeData {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "some_data_pkey"
    )
    @SequenceGenerator(
            name = "some_data_pkey",
            sequenceName = "some_data_id_seq",
            allocationSize = 1
    )
    @Column(
            name = "id",
            insertable = false,
            nullable = false
    )
    Integer id;

    @Column(
            name = "some_string",
            nullable = false
    )
    String name;
}
