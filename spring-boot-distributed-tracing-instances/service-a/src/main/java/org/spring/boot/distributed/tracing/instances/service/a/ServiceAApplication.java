package org.spring.boot.distributed.tracing.instances.service.a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Service a application.
 *
 * @author Alexander A. Kropotin
 * @project service -a
 * @created 2021 -08-08 20:03 <p>
 */
@SpringBootApplication
public class ServiceAApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ServiceAApplication.class, args);
    }
}
