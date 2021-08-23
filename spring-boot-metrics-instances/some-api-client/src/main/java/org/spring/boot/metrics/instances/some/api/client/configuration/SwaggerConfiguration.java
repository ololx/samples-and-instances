package org.spring.boot.metrics.instances.some.api.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * The type Swagger configuration.
 *
 * @author Alexander A. Kropotin
 * @project some -api -client
 * @created 2021 -07-03 12:22 <p>
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
                .apis(RequestHandlerSelectors.basePackage("org.spring.boot.metrics.instances.some.api.client"))
                .paths(regex("/.*"))
                .build()
                .apiInfo(metaData());

        return apiDocket;
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Some Data RESTful API")
                .description("Some Data CRUD Methods")
                .version("0.0.1")
                .contact(new Contact(
                        "Alexander A. Kropotin",
                        "https://github.com/ololx",
                        "ololx@icloud.ru"))
                .build();
    }
}
