package io.github.ololx.samples.monitoring;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * project prometheus-examples
 * created 01.07.2023 21:04
 *
 * @author Alexander A. Kropotin
 */
public class ExecutorMonitor implements AutoCloseable {

    private final ScheduledExecutorService heartbeatExecutor;

    private final MeasuredTaskExecutor taskExecutor;

    private final AtomicLong queueCount = new AtomicLong();

    private final AtomicLong activeCount = new AtomicLong();

    private final AtomicLong poolCount = new AtomicLong();

    private final AtomicLong completedCount = new AtomicLong();

    public ExecutorMonitor(MeasuredTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
        this.heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public void startMonitoring(long interval, TimeUnit timeUnit) {
        this.heartbeatExecutor.scheduleAtFixedRate(
            () -> {
                completedCount.set(this.taskExecutor.getActiveThreadCount());
                Metrics.gauge(
                    "executor.service.task.completed.count",
                    List.of(Tag.of("identifier", this.taskExecutor.getId())),
                    this.taskExecutor.getCompletedTaskCount()
                );

                activeCount.set(this.taskExecutor.getActiveThreadCount());
                Metrics.gauge(
                    "executor.service.task.active.count",
                    List.of(Tag.of("identifier", this.taskExecutor.getId())),
                    activeCount
                );

                queueCount.set(this.taskExecutor.getQueueSize());
                Metrics.gauge(
                    "executor.service.task.queue.count",
                    List.of(Tag.of("identifier", this.taskExecutor.getId())),
                    queueCount
                );

                poolCount.set(this.taskExecutor.getPoolSize());
                Metrics.gauge(
                    "executor.service.task.pool.count",
                    List.of(Tag.of("identifier", this.taskExecutor.getId())),
                    poolCount
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
