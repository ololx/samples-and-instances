package org.spring.security.instances.basic.authentication.instance.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

/**
 * The type User detail.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:34 <p>
 */
@ApiModel(
        value = "UserResponse",
        description = "Модель сущности \"Пользователь\""
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetail implements BasicAuthenticationInstanceDetail {

    @ApiModelProperty(
            notes = "Идентификатор пользователя",
            example = "1"
    )
    @JsonView({
            Create.class,
            Update.class,
            Delete.class,
            Retrieve.class
    })
    @JsonProperty("id")
    Optional<Integer> id;


    @ApiModelProperty(
            notes = "Идентификатор роли",
            example = "1"
    )
    @JsonView({
            Create.class,
            Update.class,
            Retrieve.class
    })
    @JsonProperty("userRoleId")
    Optional<Integer> userRoleId;

    @ApiModelProperty(
            notes = "Имя пользователя",
            example = "someMan1987"
    )
    @JsonView({
            Retrieve.class,
            Update.class
    })
    @JsonProperty("name")
    Optional<String> name;

    @ApiModelProperty(
            notes = "Пароль пользователя",
            example = "1234"
    )
    @JsonView({
            Retrieve.class,
            Update.class
    })
    @JsonProperty("password")
    Optional<String> password;

    @ApiModelProperty(
            notes = "Флаг - активный ли пользователь?",
            example = "true"
    )
    @JsonView({
            Retrieve.class,
            Update.class
    })
    @JsonProperty("isActive")
    Optional<Boolean> isActive;
}
