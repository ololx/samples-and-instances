package io.github.ololx.samples.monitoring;

/**
 * project prometheus-examples
 * created 28.06.2023 10:54
 *
 * @author Alexander A. Kropotin
 */
public interface MeasuredTaskExecutor {

    long getCompletedTaskCount();

    int getActiveThreadCount();

    int getQueueSize();

    int getPoolSize();

    String getId();
}
