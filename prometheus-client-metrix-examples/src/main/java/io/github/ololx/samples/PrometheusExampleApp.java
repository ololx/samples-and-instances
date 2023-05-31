package io.github.ololx.samples;

import io.github.ololx.samples.metrics.CounterFactory;
import io.github.ololx.samples.metrics.GaugeFactory;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * project deduplication-service-example
 * created 27.05.2023 12:48
 *
 * @author Alexander A. Kropotin
 */
public class PrometheusExampleApp implements AutoCloseable {

    static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    static final CounterFactory counterFactory = new CounterFactory();

    static final GaugeFactory gaugeFactory = new GaugeFactory();

    static final HTTPServer server;

    static {
        try {
            server = new HTTPServer.Builder()
                    .withPort(8080)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        PrometheusExampleApp app = new PrometheusExampleApp();
        app.start();
    }

    private void start() {
        executorService.scheduleAtFixedRate(() -> {
            this.countExecutions();
            this.gaugeExecutionsDate();
        }, 0, 5, TimeUnit.SECONDS
        );
    }

    private void countExecutions() {
        var now = LocalDateTime.now();
        var executions = counterFactory.getOrCreate(
                "execution_count",
                Map.of(
                        "author", "guide-man",
                        "application", this.getClass().getName(),
                        "now", now.truncatedTo(ChronoUnit.MINUTES).toString()
                )
        );
        executions.inc();
        System.out.println("Count: " + executions.get());
    }

    private void gaugeExecutionsDate() {
        var now = LocalDateTime.now();
        var executions = gaugeFactory.getOrCreate(
                "execution_gauge",
                Map.of(
                        "author", "guide-man",
                        "application", this.getClass().getName(),
                        "now", now.truncatedTo(ChronoUnit.MINUTES).toString()
                )
        );
        executions.set(now.getSecond());
        System.out.println("Gauge: " + executions.get());
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
    }
}