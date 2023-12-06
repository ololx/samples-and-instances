package io.github.ololx.samples.jackson.json.view.samples.model;

/**
 * @author Alexander A. Kropotin
 *     project jackson-json-view-samples
 *     created 01/12/2023 5:35 pm
 */
public interface CharacterView {

    public interface Title {}

    public interface Characteristics {}

    public interface Character extends Title, Characteristics {}
}
