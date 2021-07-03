package org.some.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Some application.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 2021 -07-03 11:19 <p>
 */
@SpringBootApplication
public class SomeApiApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) {
        SpringApplication.run(SomeApiApplication.class, args);
    }
}
