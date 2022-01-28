package org.restful.updating.instances.option.field.approach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The type Option field approach.
 *
 * @author Alexander A. Kropotin
 * @project restful -updating-instances
 * @created 28.06.2020 07:54 <p>
 */
@SpringBootApplication
public class OptionFieldApproach extends SpringBootServletInitializer {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(OptionFieldApproach.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OptionFieldApproach.class);
    }
}
