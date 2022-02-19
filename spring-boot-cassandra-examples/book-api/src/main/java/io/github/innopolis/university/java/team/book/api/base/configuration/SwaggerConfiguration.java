package io.github.innopolis.university.java.team.book.api.base.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

/**
 * project well-log
 * created 2021-12-09 18:36
 *
 * @author Alexander A. Kropotin
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    /**
     * The Base package.
     */
    static final String BASE_PACKAGE = "io.github.innopolis.university.java.team.spring.boot.cassandra.examples.book.api";

    /**
     * Gets product api.
     *
     * @return the product api
     */
    @Bean(name = "productApi")
    public Docket getProductApi() {
        Docket apiDocket = new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(basePackage(BASE_PACKAGE))
                .paths(regex("/.*"))
                .build();

        return apiDocket;
    }
}
