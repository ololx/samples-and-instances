package org.witsml.parsing.instances.hashmapinc.witsml.objects.library.implementation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.witsml.parsing.instances.commons.configuration.FilterConfiguration;
import org.witsml.parsing.instances.commons.configuration.SwaggerConfiguration;

/**
 * @project witsml-parsing-instances
 * @created 2021-11-15 10:49
 * <p>
 * @author Alexander A. Kropotin
 */
@SpringBootApplication(scanBasePackages = {
    "org.witsml.parsing.instances.hashmapinc.witsml.objects.library.implementation",
    "org.witsml.parsing.instances.commons"
})
public class WITSMLObjectsLibraryImplementationApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WITSMLObjectsLibraryImplementationApplication.class, args);
    }
}
