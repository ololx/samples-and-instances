package org.spring.boot.metrics.instances.some.api.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

/**
 * The type Some data detail.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 2021 -07-06 17:49 <p>
 */
@ApiModel(
        value = "SomeDataExistenceDetail",
        description = "The model of an entity \"Some Data Existence\""
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class SomeDataExistenceDetail implements SomeApiDetail {

    @ApiModelProperty(
            position = 1,
            notes = "Identifier",
            example = "1"
    )
    @JsonProperty("id")
    Integer id;

    @ApiModelProperty(
            position = 2,
            notes = "Existence",
            example = "true"
    )
    @JsonProperty("existence")
    Boolean existence;
}
