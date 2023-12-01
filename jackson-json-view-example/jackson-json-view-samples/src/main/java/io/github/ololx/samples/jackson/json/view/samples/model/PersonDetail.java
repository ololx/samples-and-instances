package io.github.ololx.samples.jackson.json.view.samples.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author Alexander A. Kropotin
 *     project jackson-json-view-samples
 *     created 01/12/2023 5:32 pm
 */
@NoArgsConstructor
@Data
@FieldDefaults(
    level = AccessLevel.PRIVATE
)
public class PersonDetail {

    @JsonView(Views.Person.class)
    String name;

    @JsonView(Views.Person.class)
    String secondName;

    @JsonView(Views.Person.class)
    int age;

    @JsonView(Views.Person.class)
    String phoneNumber;

    @JsonView(Views.Person.class)
    String email;
}
