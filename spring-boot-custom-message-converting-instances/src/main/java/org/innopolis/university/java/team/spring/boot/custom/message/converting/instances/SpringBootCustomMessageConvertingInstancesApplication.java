package org.innopolis.university.java.team.spring.boot.custom.message.converting.instances;

import lombok.SneakyThrows;
import org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.configuration.JSONTMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @project spring-boot-custom-message-converting-instances
 * @created 2021-12-05 15:09
 * <p>
 * @author Alexander A. Kropotin
 */
@SpringBootApplication
public class SpringBootCustomMessageConvertingInstancesApplication {

    public static void main(String args[]) {
        SpringApplication.run(SpringBootCustomMessageConvertingInstancesApplication.class, args);
    }

}
