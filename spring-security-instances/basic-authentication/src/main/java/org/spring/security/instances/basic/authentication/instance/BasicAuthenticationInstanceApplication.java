package org.spring.security.instances.basic.authentication.instance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Basic authentication instance application.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:08 <p>
 */
@SpringBootApplication
public class BasicAuthenticationInstanceApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) {
        SpringApplication.run(BasicAuthenticationInstanceApplication.class, args);
    }
}
