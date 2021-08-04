package org.spring.boot.distributed.tracing.instances.service.b.model.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * The type Message detail.
 *
 * @author Alexander A. Kropotin
 * @project service -b
 * @created 2021 -08-04 12:19 <p>
 */
@ApiModel(
        value = "MessageDetail",
        description = "The model of an entity \"Message\""
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
public class MessageDetail implements ServiceBDetail {

    @ApiModelProperty(
            notes = "Identifier",
            example = "1"
    )
    @JsonProperty("id")
    Integer id;

    @ApiModelProperty(
            notes = "Identifier",
            example = "foo"
    )
    @JsonProperty("content")
    String content;
}
