package org.spring.security.instances.basic.authentication.instance.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type User role.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:24 <p>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity
@Table(name = "usr_role")
public final class UserRole implements GrantedAuthority, BasicAuthenticationInstanceEntity {

    @Id
    @Column(name = "id", insertable = false, nullable = false)
    Integer id;

    @Column(name = "detail", nullable = false)
    String detail;

    @Column(name = "name", nullable = false)
    String name;

    @Override
    public String getAuthority() {
        return null;
    }
}
