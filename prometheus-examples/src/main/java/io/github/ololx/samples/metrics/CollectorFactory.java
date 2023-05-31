package io.github.ololx.samples.metrics;

import java.util.Collections;
import java.util.Map;

/**
 * project prometheus-examples
 * created 31.05.2023 20:42
 *
 * @author Alexander A. Kropotin
 */
public interface CollectorFactory<T> {

    default T getOrCreate(String name) {
        return this.getOrCreate(name, Collections.emptyMap());
    }

    T getOrCreate(String name, Map<String, String> labels);
}
