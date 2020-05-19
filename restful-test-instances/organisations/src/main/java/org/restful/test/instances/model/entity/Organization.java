package org.restful.test.instances.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        of = {
                "name"
        },
        doNotUseGetters = true
)
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "Organization")
@Table(name = "organization")
public class Organization implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "organization_pkey"
    )
    @SequenceGenerator(
            name = "organization_pkey",
            sequenceName = "organization_uid_seq",
            allocationSize = 1
    )
    @Column(
            name = "id",
            insertable = false,
            nullable = false
    )
    Long id;

    @Size(
            max = 10,
            message = "Наименование организации должно быть до 30 символов"
    )
    @NotBlank(
            message = "Наименование организации должно быть задано"
    )
    @Column(name = "name", nullable = false)
    String name;

    @Size(
            max = 10,
            message = "ИНН организации должен быть до 10 символов"
    )
    @Column(name = "inn")
    String inn;

    @Size(
            max = 8,
            message = "КПП организации должен быть до 10 символов"
    )
    @Column(name = "kpp")
    String kpp;

    @Size(
            max = 100,
            message = "Адрес организации должен быть до 100 символов"
    )
    @Column(name = "address")
    String address;
}