package org.spring.boot.distributed.tracing.instances.service.b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Service b jaeger application.
 *
 * @author Alexander A. Kropotin
 * @project service -b
 * @created 2021 -08-04 11:02 <p>
 */
@SpringBootApplication
public class ServiceBJaegerApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ServiceBJaegerApplication.class, args);
    }
}
