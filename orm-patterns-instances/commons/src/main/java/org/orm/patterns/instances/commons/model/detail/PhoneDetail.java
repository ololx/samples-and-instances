package org.orm.patterns.instances.commons.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * The type Phone detail.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        of = {
                "id"
        },
        doNotUseGetters = true
)
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class PhoneDetail implements Serializable {

    /**
     * The Id.
     */
    @JsonProperty("id")
    Optional<Long> id;

    /**
     * The Person id.
     */
    @JsonProperty("person_id")
    Optional<Long> personId;

    /**
     * The Number.
     */
    @JsonProperty("number")
    Optional<String> number;
}