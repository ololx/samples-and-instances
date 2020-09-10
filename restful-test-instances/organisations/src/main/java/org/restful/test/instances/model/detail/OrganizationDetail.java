package org.restful.test.instances.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Optional;

/**
 * @project restful-test-instances
 * @created 11.06.2020 10:07
 * <p>
 * @author Alexander A. Kropotin
 */
@ApiModel(
        value = "OrganizationDetail",
        description = "Модель сущности \"Организация\""
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        of = {
                "uid",
                "name"
        },
        doNotUseGetters = true
)
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class OrganizationDetail implements Serializable {

    @ApiModelProperty(
            position = 1,
            notes = "Идентификатор",
            example = "1"
    )
    @JsonProperty("uid")
    Optional<Long> uid;

    @ApiModelProperty(
            position = 2,
            notes = "Наименование",
            example = "WCorp"
    )
    @JsonProperty("name")
    Optional<String> name;

    @ApiModelProperty(
            position = 3,
            notes = "ИНН",
            example = "01234567"
    )
    @JsonProperty("inn")
    Optional<String> inn;

    @ApiModelProperty(
            position = 4,
            notes = "КПП",
            example = "01234567"
    )
    @JsonProperty("kpp")
    Optional<String> kpp;

    @ApiModelProperty(
            position = 5,
            notes = "Адрес",
            example = "Новая - 15, 2"
    )
    @JsonProperty("address")
    Optional<String> address;
}