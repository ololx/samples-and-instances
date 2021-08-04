package org.spring.boot.distributed.tracing.instances.service.b.jaeger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @project service-b
 * @created 2021-08-04 11:02
 * <p>
 * @author Alexander A. Kropotin
 */
@SpringBootApplication
public class ServiceBJaegerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceBJaegerApplication.class, args);
    }
}
