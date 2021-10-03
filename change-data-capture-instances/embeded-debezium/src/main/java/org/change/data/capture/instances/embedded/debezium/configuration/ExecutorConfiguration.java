package org.change.data.capture.instances.embedded.debezium.configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Executor configuration.
 *
 * @author Alexander A. Kropotin
 * @project embeded -debezium
 * @created 2021 -10-03 20:34 <p>
 */
@Slf4j
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Configuration
public class ExecutorConfiguration {

    /**
     * Gets executor.
     *
     * @return the executor
     */
    @Bean(name = "Executor")
    public ExecutorService getExecutor() {
        return Executors.newCachedThreadPool();
    }
}
