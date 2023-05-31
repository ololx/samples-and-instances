package io.github.ololx.samples.metrics;

import io.prometheus.client.Gauge;

/**
 * project prometheus-examples
 * created 31.05.2023 20:42
 *
 * @author Alexander A. Kropotin
 */
public class GaugeFactory extends AbstractCollectorFactory<Gauge.Child, Gauge> {

    protected Gauge create(String name, String[] labelsNames) {
        return Gauge.build()
                .name(name)
                .help(name)
                .labelNames(labelsNames)
                .register();
    }
}
