package io.github.ololx.samples;

import io.github.ololx.samples.metrics.CounterFactory;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
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

    static HTTPServer server;

    public static void main(String[] args) throws IOException {
        PrometheusExampleApp app = new PrometheusExampleApp();
        server = new HTTPServer.Builder()
                .withPort(8080)
                .build();
        app.start();
    }

    private void start() {
        executorService.scheduleAtFixedRate(this::countExecutions, 0, 1, TimeUnit.SECONDS);
    }

    private void countExecutions() {
        var executions = counterFactory.getOrCreate(
                "execution_count",
                Map.of("author", "guide-man", "application", this.getClass().getName())
        );
        executions.inc();
        System.out.println("Count: " + executions.get());
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
    }
}