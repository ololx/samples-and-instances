package io.github.ololx.samples.monitoring;

import io.github.ololx.moonshine.stopwatch.SimpleStopwatch;
import io.github.ololx.moonshine.stopwatch.Stopwatch;
import io.micrometer.core.instrument.Metrics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * project prometheus-examples
 * created 28.06.2023 10:54
 *
 * @author Alexander A. Kropotin
 */
public class MeasuredExecutorService implements MeasuredTaskExecutor, ExecutorService {

    private static final AtomicLong executorServiceNumber = new AtomicLong();

    protected final ExecutorService executorService;

    protected final String identifier;

    public MeasuredExecutorService(ExecutorService executorService) {
        this(executorService, String.format("MeasuredExecutorService-%s", executorServiceNumber.incrementAndGet()));
    }

    public MeasuredExecutorService(ExecutorService executorService, String identifier) {
        this.executorService = Objects.requireNonNull(executorService);
        this.identifier = Objects.requireNonNull(identifier);
    }

    @Override
    public void shutdown() {
        this.executorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return this.executorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return this.executorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return this.executorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.executorService.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        Stopwatch stopwatch = new SimpleStopwatch().start();

        return this.executorService.submit(
            () -> {
                Metrics.timer("executor.service.task.wait.execution.time", "task", String.valueOf(task))
                    .record(stopwatch.elapsed().toNanos(), TimeUnit.NANOSECONDS);
                return Objects.requireNonNull(task).call();
            });
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        Stopwatch stopwatch = new SimpleStopwatch().start();

        return this.executorService.submit(
            () -> {
                Metrics.timer("executor.service.task.wait.execution.time", "task", String.valueOf(task))
                    .record(stopwatch.elapsed().toNanos(), TimeUnit.NANOSECONDS);
                Objects.requireNonNull(task).run();
            },
            result
        );
    }

    @Override
    public Future<?> submit(Runnable task) {
        Stopwatch stopwatch = new SimpleStopwatch().start();

        return this.executorService.submit(
            () -> {
                Metrics.timer("executor.service.task.wait.execution.time", "task", String.valueOf(task))
                    .record(stopwatch.elapsed().toNanos(), TimeUnit.NANOSECONDS);
                Objects.requireNonNull(task).run();
            }
        );
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        Stopwatch stopwatch = new SimpleStopwatch().start();

        List<Callable<T>> measuredTasks = new ArrayList<>();
        Objects.requireNonNull(tasks).forEach(task -> {
            measuredTasks.add(() -> {
                Metrics.timer("executor.service.task.wait.execution.time", "task", String.valueOf(task))
                    .record(stopwatch.elapsed().toNanos(), TimeUnit.NANOSECONDS);

                return Objects.requireNonNull(task).call();
            });
        });

        return this.executorService.invokeAll(measuredTasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        Stopwatch stopwatch = new SimpleStopwatch().start();

        List<Callable<T>> measuredTasks = new ArrayList<>();
        Objects.requireNonNull(tasks).forEach(task -> {
            measuredTasks.add(() -> {
                Metrics.timer("executor.service.task.wait.execution.time", "task", String.valueOf(task))
                    .record(stopwatch.elapsed().toNanos(), TimeUnit.NANOSECONDS);

                return Objects.requireNonNull(task).call();
            });
        });

        return this.executorService.invokeAll(measuredTasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        Stopwatch stopwatch = new SimpleStopwatch().start();

        List<Callable<T>> measuredTasks = new ArrayList<>();
        Objects.requireNonNull(tasks).forEach(task -> {
            measuredTasks.add(() -> {
                Metrics.timer("executor.service.task.wait.execution.time", "task", String.valueOf(task))
                    .record(stopwatch.elapsed().toNanos(), TimeUnit.NANOSECONDS);

                return Objects.requireNonNull(task).call();
            });
        });

        return this.executorService.invokeAny(measuredTasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        Stopwatch stopwatch = new SimpleStopwatch().start();

        List<Callable<T>> measuredTasks = new ArrayList<>();
        Objects.requireNonNull(tasks).forEach(task -> {
            measuredTasks.add(() -> {
                Metrics.timer("executor.service.task.wait.execution.time", "task", String.valueOf(task))
                    .record(stopwatch.elapsed().toNanos(), TimeUnit.NANOSECONDS);

                return Objects.requireNonNull(task).call();
            });
        });

        return this.executorService.invokeAny(measuredTasks, timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        Stopwatch stopwatch = new SimpleStopwatch().start();
        this.executorService.execute(
            () -> {
                Metrics.timer("executor.service.task.wait.execution.time")
                    .record(stopwatch.elapsed().toNanos(), TimeUnit.NANOSECONDS);
                command.run();
            }
        );
    }

    public long getCompletedTaskCount() {
        return ((ThreadPoolExecutor) this.executorService).getCompletedTaskCount();
    }

    public int getActiveThreadCount() {
        return ((ThreadPoolExecutor) this.executorService).getActiveCount();
    }

    public int getQueueSize() {
        return ((ThreadPoolExecutor) this.executorService).getQueue().size();
    }

    @Override
    public int getPoolSize() {
        return ((ThreadPoolExecutor) this.executorService).getPoolSize();
    }

    @Override
    public String getId() {
        return this.identifier;
    }
}
