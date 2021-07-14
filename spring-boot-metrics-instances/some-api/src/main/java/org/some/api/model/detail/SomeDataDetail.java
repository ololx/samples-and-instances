package org.some.api.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

/**
 * @project some-api
 * @created 2021-07-06 17:49
 * <p>
 * @author Alexander A. Kropotin
 */
@ApiModel(
        value = "SomeDataDetail",
        description = "The model of an entity \"Some Data\""
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SomeDataDetail implements SomeApiDetail {

    @ApiModelProperty(
            position = 1,
            notes = "Identifier",
            example = "1"
    )
    @JsonView({
            Create.class,
            Retrieve.class,
            Update.class,
            Delete.class
    })
    @JsonProperty("id")
    Optional<Integer> id;

    @ApiModelProperty(
            position = 2,
            notes = "Some String",
            example = "bla bla"
    )
    @JsonView({
            Create.class,
            Retrieve.class
    })
    @JsonProperty("someString")
    Optional<String> someString;
}
