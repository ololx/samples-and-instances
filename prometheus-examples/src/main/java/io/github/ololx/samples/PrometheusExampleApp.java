package io.github.ololx.samples;

import io.github.ololx.samples.metrics.CounterFactory;
import io.github.ololx.samples.metrics.GaugeFactory;
import io.github.ololx.samples.metrics.HistogramFactory;
import io.github.ololx.samples.metrics.MetricableScheduledExecutorService;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * project deduplication-service-example
 * created 27.05.2023 12:48
 *
 * @author Alexander A. Kropotin
 */
public class PrometheusExampleApp implements AutoCloseable {

    static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    static final MetricableScheduledExecutorService metricableScheduledExecutorService = new MetricableScheduledExecutorService(Executors.newFixedThreadPool(4));

    static final CounterFactory counterFactory = new CounterFactory();

    static final GaugeFactory gaugeFactory = new GaugeFactory();

    static final HistogramFactory histogramFactory = new HistogramFactory();

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
        List<CompletableFuture<Void>> completableFutures = IntStream.range(0, 5)
                                                                    .mapToObj(index -> CompletableFuture.runAsync(
                                                                      () -> {
                                                                          System.out.println("index - " + index);
                                                                          try {
                                                                              Thread.sleep(1000);
                                                                          } catch(InterruptedException e) {
                                                                              throw new RuntimeException(e);
                                                                          }
                                                                      },
                                                                              metricableScheduledExecutorService)
                                                                    )
                                                                    .toList();
        executorService.scheduleAtFixedRate(() -> {
            this.countExecutions();
            this.gaugeExecutionsDate();
            this.histogramExecutionsDate();
        }, 0, 5, TimeUnit.SECONDS
        );
        completableFutures.parallelStream().forEach(CompletableFuture::join);
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

    private void histogramExecutionsDate() {
        var now = LocalDateTime.now();
        var executions = histogramFactory.getOrCreate(
                "execution_histogram",
                Map.of(
                        "author", "guide-man",
                        "application", this.getClass().getName(),
                        "now", now.truncatedTo(ChronoUnit.MINUTES).toString()
                )
        );
        executions.observe(now.getSecond());
        System.out.println("Histogram: " + executions.get().sum);
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
    }
}