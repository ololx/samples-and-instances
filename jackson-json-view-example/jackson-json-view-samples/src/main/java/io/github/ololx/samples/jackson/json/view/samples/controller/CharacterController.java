package io.github.ololx.samples.jackson.json.view.samples.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.ololx.samples.jackson.json.view.samples.model.CharacterDetail;
import io.github.ololx.samples.jackson.json.view.samples.model.CharacterView;
import io.github.ololx.samples.jackson.json.view.samples.service.CharacterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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
@RequestMapping("character")
@RestController
public class CharacterController {

    CharacterService characterService;

    @JsonView(CharacterView.Character.class)
    @GetMapping
    public Collection<CharacterDetail> getAllCharacters() {
        return characterService.getAll();
    }

    @GetMapping("/title")
    @JsonView(CharacterView.Title.class)
    public Collection<CharacterDetail> getAllTitles() {
        return characterService.getAll();
    }

    @GetMapping("/characteristic")
    @JsonView(CharacterView.Characteristics.class)
    public Collection<CharacterDetail> getAllCharacteristics() {
        return characterService.getAll();
    }
}
