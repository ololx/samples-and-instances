package org.restful.querying.instances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MainCriteriaBuilder extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainCriteriaBuilder.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainCriteriaBuilder.class);
    }
}
