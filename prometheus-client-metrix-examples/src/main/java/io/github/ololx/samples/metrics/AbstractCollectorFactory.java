package io.github.ololx.samples.metrics;

import io.prometheus.client.SimpleCollector;

import java.util.*;

/**
 * project prometheus-examples
 * created 31.05.2023 20:42
 *
 * @author Alexander A. Kropotin
 */
public abstract class AbstractCollectorFactory<R, T extends SimpleCollector<R>> implements CollectorFactory<R> {

    protected Map<String, T> collectors = new HashMap<>();

    public R getOrCreate(String name, Map<String, String> labels) {
        var collector = this.collectors.get(name);

        if (collector == null) {
            collector = this.create(name, labels.keySet().toArray(new String[0]));
            this.collectors.put(name, collector);
        }

        return collector.labels(labels.values().toArray(new String[0]));
    }

    abstract protected T create(String name, String[] labelsNames);
}
