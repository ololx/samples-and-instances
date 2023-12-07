package io.github.ololx.samples.jackson.json.view.samples.service;

import io.github.ololx.samples.jackson.json.view.samples.model.CharacterDetail;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Alexander A. Kropotin
 *     project jackson-json-view-samples
 *     created 06/12/2023 5:49 pm
 */
@RequiredArgsConstructor
@FieldDefaults(
    level = AccessLevel.PRIVATE,
    makeFinal = true
)
@Service
public class CharacterService {

    StoredCharactersDetails storedCharactersDetails;

    public Collection<CharacterDetail> getAll() {
        return storedCharactersDetails.values();
    }
}
