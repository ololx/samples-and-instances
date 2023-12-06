package io.github.ololx.samples.jackson.json.view.samples.controller;

import io.github.ololx.samples.jackson.json.view.samples.service.CharacterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alexander A. Kropotin
 *     project jackson-json-view-samples
 *     created 06/12/2023 5:47 pm
 */
@RequiredArgsConstructor
@FieldDefaults(
    level = AccessLevel.PRIVATE,
    makeFinal = true
)
@RestController
public class CharacterController {

    CharacterService characterService;

}
