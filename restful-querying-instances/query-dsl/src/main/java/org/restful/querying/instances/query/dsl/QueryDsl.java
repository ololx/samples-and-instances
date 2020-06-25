package org.restful.querying.instances.specification.builder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * The type Query dsl.
 *
 * @author Alexander A. Kropotin
 * @project restful -querying-instances
 * @created 24.05.2020 07:54 <p>
 */
@EnableSpringDataWebSupport
@SpringBootApplication
public class QueryDsl extends SpringBootServletInitializer {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(QueryDsl.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(QueryDsl.class);
    }
}
