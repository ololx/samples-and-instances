package org.spring.security.instances.basic.authentication.instance.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The type User.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:29 <p>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity
@Table(name = "usr")
public final class User implements BasicAuthenticationInstanceEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator="usr_pkey"
    )
    @SequenceGenerator(name="usr_pkey",
            sequenceName="usr_id_seq",
            allocationSize=1
    )
    @Column(name = "id", insertable = false, nullable = false)
    Integer id;

    @NotNull(
            message = "Роль пользователя должна быть задана"
    )
    @Column(name = "usr_role_id", nullable = false)
    Integer userRoleId;

    @NotBlank(
            message = "Имя пользователя должно быть задано"
    )
    @Column(name = "name")
    String name;

    @NotBlank(
            message = "Пароль должен быть задан"
    )
    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "is_active", nullable = false)
    Boolean isActive = true;
}
