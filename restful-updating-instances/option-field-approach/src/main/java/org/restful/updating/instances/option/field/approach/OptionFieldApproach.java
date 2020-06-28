package org.restful.updating.instances.option.field.approach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @project restful-updating-instances
 * @created 28.06.2020 07:54
 * <p>
 * @author Alexander A. Kropotin
 */
@SpringBootApplication
public class OptionFieldApproach extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(OptionFieldApproach.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OptionFieldApproach.class);
    }
}
