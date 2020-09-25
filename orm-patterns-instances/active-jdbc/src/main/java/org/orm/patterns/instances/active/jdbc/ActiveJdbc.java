package org.orm.patterns.instances.active.jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The type ActiveJdbc configuration.
 */
@SpringBootApplication
public class ActiveJdbc extends SpringBootServletInitializer {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ActiveJdbc.class, args);
    }

    /**
     * Configure spring application builder.
     *
     * @param application the application
     * @return the spring application builder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ActiveJdbc.class);
    }
}
