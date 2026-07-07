package org.restful.test.instances.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * @project restful-test-instances
 * @created 03.04.2021 10:15
 * <p>
 * @author Alexander A. Kropotin
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(
        of = {
                "code"
        }
)
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "Position")
@Table(name = "position")
public final class Position {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "position_pkey"
    )
    @SequenceGenerator(
            name = "position_pkey",
            sequenceName = "position_position_uid_seq",
            allocationSize = 1
    )
    Short positionUid;

    @Column(name = "name")
    String name;

    @Column(name = "code")
    String code;
}
