package io.github.ololx.samples.metrics;

import io.github.ololx.moonshine.stopwatch.SimpleStopwatch;
import io.github.ololx.moonshine.stopwatch.Stopwatch;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * project prometheus-examples
 * created 28.06.2023 10:54
 *
 * @author Alexander A. Kropotin
 */
public class MetricableScheduledExecutorService implements ExecutorService {

    ExecutorService scheduledExecutorService;

    public MetricableScheduledExecutorService(ExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public void shutdown() {
        this.scheduledExecutorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return this.scheduledExecutorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return this.scheduledExecutorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return this.scheduledExecutorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.scheduledExecutorService.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        Stopwatch stopwatch = new SimpleStopwatch().start();
        return this.scheduledExecutorService.submit(() -> {
            System.out.println("Waited in queue: " + stopwatch.elapsed());
            return task.call();
        });
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return this.scheduledExecutorService.submit(task, result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return this.scheduledExecutorService.submit(task);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return this.scheduledExecutorService.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return this.scheduledExecutorService.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return this.scheduledExecutorService.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.scheduledExecutorService.invokeAny(tasks);
    }

    @Override
    public void execute(Runnable command) {
        Stopwatch stopwatch = new SimpleStopwatch().start();
        this.scheduledExecutorService.execute(() -> {
            System.out.println("Waited in queue: " + stopwatch.elapsed());
            command.run();
        });
    }
}
