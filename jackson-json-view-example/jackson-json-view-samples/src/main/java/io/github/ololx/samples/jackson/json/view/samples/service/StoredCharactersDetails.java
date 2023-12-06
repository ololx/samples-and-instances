package io.github.ololx.samples.jackson.json.view.samples.service;

import io.github.ololx.samples.jackson.json.view.samples.model.CharacterDetail;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexander A. Kropotin
 *     project jackson-json-view-samples
 *     created 06/12/2023 5:49 pm
 */
@Component
public class StoredCharactersDetails extends ConcurrentHashMap<Integer, CharacterDetail> {

    private static final Map<Integer, CharacterDetail> storedCharactersDetails = Map.of(
        1, new CharacterDetail(1, "Alan", "Sleep", 23, 173, 80.2),
        2, new CharacterDetail(1, "Curious", "Sam", 30, 190, 100),
        3, new CharacterDetail(1, "Duke", "McKen", 100, 200, 120),
        4, new CharacterDetail(1, "Max", "Painkiller", 33, 173, 85.7)
    );

    public StoredCharactersDetails() {
        super(storedCharactersDetails);
    }
}
