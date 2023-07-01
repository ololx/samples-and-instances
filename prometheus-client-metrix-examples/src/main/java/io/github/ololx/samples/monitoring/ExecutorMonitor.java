package io.github.ololx.samples.monitoring;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * project prometheus-examples
 * created 01.07.2023 21:04
 *
 * @author Alexander A. Kropotin
 */
public class ExecutorMonitor implements AutoCloseable {

    private final ScheduledExecutorService heartbeatExecutor;

    private final MeasuredTaskExecutor taskExecutor;

    public ExecutorMonitor(MeasuredTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
        this.heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startMonitoring(long interval, TimeUnit timeUnit) {
        this.heartbeatExecutor.scheduleAtFixedRate(
            () -> {
                Metrics.gauge(
                    "executor.service.task.completed.count",
                    List.of(Tag.of("identifier", this.taskExecutor.getId())),
                    this.taskExecutor.getCompletedTaskCount()
                );
                Metrics.gauge(
                    "executor.service.task.active.count",
                    List.of(Tag.of("identifier", this.taskExecutor.getId())),
                    this.taskExecutor.getActiveThreadCount()
                );
                Metrics.gauge(
                    "executor.service.task.queue.count",
                    List.of(Tag.of("identifier", this.taskExecutor.getId())),
                    this.taskExecutor.getQueueSize()
                );
                Metrics.gauge(
                    "executor.service.task.pool.count",
                    List.of(Tag.of("identifier", this.taskExecutor.getId())),
                    this.taskExecutor.getPoolSize()
                );
            },
            0, interval, timeUnit
        );
    }

    @Override
    public void close() throws Exception {
        this.heartbeatExecutor.shutdown();
    }
}
