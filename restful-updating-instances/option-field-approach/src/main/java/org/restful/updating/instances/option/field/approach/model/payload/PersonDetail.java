package org.restful.updating.instances.option.field.approach.model.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

/**
 * The type Person detail.
 *
 * @author Alexander A. Kropotin
 * @project restful -updating-instances
 * @created 24.05.2020 10:52 <p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDetail {

    @JsonProperty("uid")
    Optional<Long> uid;

    @JsonProperty("first_name")
    Optional<String> firstName;

    @JsonProperty("last_name")
    Optional<String> lastName;

    @JsonProperty("age")
    Optional<Integer> age;
}
