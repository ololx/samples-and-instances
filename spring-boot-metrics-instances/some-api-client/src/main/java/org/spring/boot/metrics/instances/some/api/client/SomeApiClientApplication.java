package org.spring.boot.metrics.instances.some.api.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Some api client application.
 *
 * @author Alexander A. Kropotin
 * @project some -api-client
 * @created 2021 -07-20 17:06 <p>
 */
@SpringBootApplication
public class SomeApiClientApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) {
        SpringApplication.run(SomeApiClientApplication.class, args);
    }
}
