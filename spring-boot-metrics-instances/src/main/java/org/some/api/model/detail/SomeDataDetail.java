package org.some.api.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

/**
 * @project some-api
 * @created 2021-07-06 17:49
 * <p>
 * @author Alexander A. Kropotin
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SomeDataDetail implements SomeApiDetail {

    @JsonProperty("id")
    Optional<Integer> id;

    @JsonProperty("someString")
    Optional<String> someString;
}
