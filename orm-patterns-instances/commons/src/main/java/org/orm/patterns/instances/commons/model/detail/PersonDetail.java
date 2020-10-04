package org.orm.patterns.instances.commons.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Optional;

/**
 * The type Person detail.
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
public final class PersonDetail implements Serializable {

    /**
     * The Id.
     */
    @JsonProperty("id")
    Optional<Long> id;

    /**
     * The First name.
     */
    @JsonProperty("first_name")
    Optional<String> firstName;

    /**
     * The Last name.
     */
    @JsonProperty("last_name")
    Optional<String> lastName;

    /**
     * The Age.
     */
    @JsonProperty("age")
    Optional<Integer> age;
}