package io.github.ololx.samples;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
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

    static final Counter executions = Counter.build()
            .name("execution_count")
            .help("Method execution count")
            .labelNames("author", "application")
            .register();

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
        executions.labels("1", "2").inc();
        System.out.println("Count: " + executions.labels("1", "2").get());
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
    }
}