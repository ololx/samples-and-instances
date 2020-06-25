package org.restful.querying.instances.specification.builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @project restful-querying-instances
 * @created 24.05.2020 07:54
 * <p>
 * @author Alexander A. Kropotin
 */
@SpringBootApplication
public class SpecificationBuilder extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpecificationBuilder.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpecificationBuilder.class);
    }
}
