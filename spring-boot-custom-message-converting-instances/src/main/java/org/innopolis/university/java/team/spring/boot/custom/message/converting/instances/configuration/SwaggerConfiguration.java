package org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @project spring-boot-custom-message-converting-instances
 * @created 2021-12-05 16:20
 * <p>
 * @author Alexander A. Kropotin
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

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
                .apis(RequestHandlerSelectors.basePackage("org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.controller"))
                .paths(regex("/.*"))
                .build();

        return apiDocket;
    }
}
