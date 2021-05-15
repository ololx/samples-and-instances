package org.restful.test.instances.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @project restful-test-instances
 * @created 03.04.2021 08:56
 * <p>
 * @author Alexander A. Kropotin
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(
        of = {
                "organizationUid",
                "positionUid",
                "fullName"
        }
)
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "Employ")
@Table(name = "employ")
public final class Employ {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employ_pkey"
    )
    @SequenceGenerator(
            name = "employ_pkey",
            sequenceName = "employ_employ_uid_seq",
            allocationSize = 1
    )
    Long employUid;

    @NotNull(
            message = "У сотрудника должна быть указана организация"
    )
    @Column(name = "organization_uid")
    Long organizationUid;

    @NotNull(
            message = "У сотрудника должна быть указана должность"
    )
    @Column(name = "position_uid", nullable = true)
    Short positionUid;

    @Size(
            min = 5,
            max = 100,
            message = "Ф.И.О. сотрудника должно быть указано в диапазоне от 5-ти до 100 символов"
    )
    @NotBlank(
            message = "У сотрудника должно быть указано Ф.И.О"
    )
    @Column(name = "full_name")
    String fullName;
}
