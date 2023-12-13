package io.github.ololx.samples.jackson.json.view.samples.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author Alexander A. Kropotin
 *     project jackson-json-view-samples
 *     created 01/12/2023 5:32 pm
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(
    level = AccessLevel.PRIVATE
)
public class CharacterDetail {

    @JsonView({
        CharacterView.Title.class,
        CharacterView.Characteristics.class
    })
    int id;

    @JsonView(CharacterView.Title.class)
    String name;

    @JsonView(CharacterView.Title.class)
    String secondName;

    @JsonView(CharacterView.Characteristics.class)
    int age;

    @JsonView(CharacterView.Characteristics.class)
    int height;

    @JsonView(CharacterView.Characteristics.class)
    double weight;
}
