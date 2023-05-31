package io.github.ololx.samples.metrics;

import io.prometheus.client.Counter;
import io.prometheus.client.SimpleCollector;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * project prometheus-examples
 * created 31.05.2023 20:42
 *
 * @author Alexander A. Kropotin
 */
public class CounterFactory extends AbstractCollectorFactory<Counter.Child, Counter> {

    protected Counter create(String name, String[] labelsNames) {
        return Counter.build()
                .name(name)
                .help(name)
                .labelNames(labelsNames)
                .register();
    }
}
