package org.restful.test.instances.db.local.test.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        of = {
                "id",
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
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id",
            insertable = false,
            nullable = false
    )
    Long id;

    @NotBlank(
            message = "Наименование организации должно быть задано"
    )
    @Column(name = "name")
    String name;

    @Column(name = "inn")
    String inn;

    @Column(name = "kpp")
    String kpp;

    @Column(name = "address")
    String address;
}