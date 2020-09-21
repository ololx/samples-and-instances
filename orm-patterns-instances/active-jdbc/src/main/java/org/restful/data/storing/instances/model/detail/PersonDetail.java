package org.restful.data.storing.instances.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Optional;

@ApiModel(
        value = "PersonDetail",
        description = "Модель сущности \"Person\""
)
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class PersonDetail implements Serializable {


    @ApiModelProperty(
            position = 1,
            notes = "Идентификатор",
            example = "1"
    )
    @JsonProperty("uid")
    Optional<Long> uid;

    @ApiModelProperty(
            position = 2,
            notes = "Имя",
            example = "Alex"
    )
    @JsonProperty("first_name")
    Optional<String> firstName;

    @ApiModelProperty(
            position = 3,
            notes = "Фамилия",
            example = "Springovsky"
    )
    @JsonProperty("last_name")
    Optional<String> lastName;

    @ApiModelProperty(
            position = 4,
            notes = "Возраст",
            example = "101"
    )
    @JsonProperty("age")
    Optional<Integer> age;
}