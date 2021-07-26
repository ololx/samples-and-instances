package org.spring.security.instances.basic.authentication.instance.configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
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
 * @project basic -authentication
 * @created 2021 -07-26 09:11 <p>
 */
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Value("${swagger.package}")
    String basePackage;

    @Value("${swagger.title}")
    String title;

    @Value("${swagger.version}")
    String version;

    @Value("${swagger.description}")
    String description;

    @Value("${swagger.contact.name}")
    String contactName;

    @Value("${swagger.contact.url}")
    String contactUrl;

    @Value("${swagger.contact.email}")
    String contactEmail;

    /**
     * Gets product api.
     *
     * @return the product api
     */
    @Bean(name = "productApi")
    public Docket getProductApi() {
        Docket apiDocket = new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(regex("/.*"))
                .build()
                .apiInfo(metaData());

        return apiDocket;
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .contact(new Contact(
                        contactName,
                        contactUrl,
                        contactEmail))
                .build();
    }

}
