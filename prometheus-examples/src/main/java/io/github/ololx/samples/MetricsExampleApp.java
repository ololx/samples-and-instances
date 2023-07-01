package io.github.ololx.samples;

import io.github.ololx.moonshine.stopwatch.SimpleStopwatch;
import io.github.ololx.moonshine.stopwatch.Stopwatch;
import io.github.ololx.samples.monitoring.ExecutorMonitor;
import io.github.ololx.samples.monitoring.MeasuredExecutorService;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * project deduplication-service-example
 * created 27.05.2023 12:48
 *
 * @author Alexander A. Kropotin
 */
public class MetricsExampleApp implements AutoCloseable {

    private static final Logger log = Logger.getLogger(MetricsExampleApp.class.getName());

    private static final String[] DEFAULT_METRICS_LABELS = new String[]{"app", MetricsExampleApp.class.getName()};

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private final MeasuredExecutorService measuredExecutorService = new MeasuredExecutorService(Executors.newFixedThreadPool(3));

    private final ExecutorMonitor executorMonitor;

    private final String[] metricsLabels;

    MetricsExampleApp(String[] metricsLabels) {
        this.metricsLabels = Objects.requireNonNull(metricsLabels);
        this.executorMonitor = new ExecutorMonitor(Objects.requireNonNull(measuredExecutorService));
    }

    public static void main(String[] args) throws IOException {
        try {
            var meterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
            var server = new HTTPServer.Builder()
                .withPort(8080)
                .withRegistry(meterRegistry.getPrometheusRegistry())
                .build();
            Metrics.addRegistry(meterRegistry);

            var exampleApplication = new MetricsExampleApp(DEFAULT_METRICS_LABELS);
            exampleApplication.start();
        } catch (Exception e) {
            log.severe("Can't start application: " + e);
            System.exit(1);
        }
    }

    private void start() {
        executorMonitor.startMonitoring(1, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(
            () -> {
                CompletableFuture.allOf(
                    this.countExecutions(),
                    this.timeExecutions(),
                    this.gaugeExecutionsDate()
                );
            },
            0, 1, TimeUnit.SECONDS
        );
    }

    private CompletableFuture<Void> countExecutions() {
        return CompletableFuture.runAsync(
            () -> {
                var executions = Metrics.counter("execution count");
                executions.increment();
                log.info("Execution count: " + executions);
            },
            measuredExecutorService
        );
    }

    private CompletableFuture<Void> timeExecutions() {
        Stopwatch stopwatch = new SimpleStopwatch().start();

        return CompletableFuture.runAsync(
            () -> {
                var executions = Metrics.timer("execution time");
                var duration = stopwatch.elapsed();
                executions.record(duration.toNanos(), TimeUnit.NANOSECONDS);
                log.info("Execution gauge: " + executions);
            },
            measuredExecutorService
        );
    }

    private CompletableFuture<Void> gaugeExecutionsDate() {
        var now = LocalDateTime.now();

        return CompletableFuture.runAsync(
            () -> {
                var executions = Metrics.gauge("execution gauge", now.getSecond());
                log.info("Execution gauge: " + executions);
            },
            measuredExecutorService
        );
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
    }
}
