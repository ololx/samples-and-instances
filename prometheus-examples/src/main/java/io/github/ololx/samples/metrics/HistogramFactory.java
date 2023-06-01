package io.github.ololx.samples.metrics;

import io.prometheus.client.Histogram;

/**
 * project prometheus-examples
 * created 31.05.2023 20:42
 *
 * @author Alexander A. Kropotin
 */
public class HistogramFactory extends AbstractCollectorFactory<Histogram.Child, Histogram> {

    protected Histogram create(String name, String[] labelsNames) {
        return Histogram.build()
                .name(name)
                .help(name)
                .labelNames(labelsNames)
                .register();
    }
}
