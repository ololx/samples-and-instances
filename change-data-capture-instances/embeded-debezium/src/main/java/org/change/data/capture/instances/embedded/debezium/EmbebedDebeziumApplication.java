package org.change.data.capture.instances.embedded.debezium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Embebed debezium application.
 *
 * @author Alexander A. Kropotin
 * @project embeded -debezium
 * @created 2021 -09-08 14:46 <p>
 */
@SpringBootApplication
public class EmbebedDebeziumApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EmbebedDebeziumApplication.class, args);
    }
}
