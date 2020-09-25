package org.orm.patterns.instances.active.jdbc.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Optional;

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
public final class PersonDetail implements Serializable {

    @JsonProperty("id")
    Optional<Long> id;

    @JsonProperty("first_name")
    Optional<String> firstName;

    @JsonProperty("last_name")
    Optional<String> lastName;

    @JsonProperty("age")
    Optional<Integer> age;
}