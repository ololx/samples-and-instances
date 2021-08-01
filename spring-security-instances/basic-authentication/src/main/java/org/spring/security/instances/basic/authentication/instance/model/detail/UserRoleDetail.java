package org.spring.security.instances.basic.authentication.instance.model.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

/**
 * The type User role detail.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:37 <p>
 */
@ApiModel(
        value = "UserRoleDetail",
        description = "Модель сущности \"Роль пользователя\""
)
@Builder
@EqualsAndHashCode(
        of = {
                "id"
        },
        doNotUseGetters = true
)
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class UserRoleDetail implements BasicAuthenticationInstanceDetail {
    
    @ApiModelProperty(
            notes = "Идентификатор",
            example = "1"
    )
    @JsonView({
            Create.class,
            Retrieve.class
    })
    @JsonProperty("id")
    Optional<Integer> id;

    @ApiModelProperty(
            notes = "Наименование",
            example = "ADMIN"
    )
    @JsonView({
            Retrieve.class
    })
    @JsonProperty("name")
    Optional<String> name;

    @ApiModelProperty(
            notes = "Описание",
            example = "ККК"
    )
    @JsonView({
            Retrieve.class
    })
    @JsonProperty("detail")
    Optional<String> detail;
}
