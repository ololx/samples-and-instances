package org.orm.patterns.instances.hibernate.jpa;

import org.orm.patterns.instances.commons.CommonsApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type ActiveJdbc configuration.
 */
@SpringBootApplication(scanBasePackageClasses = {
        CommonsApplication.class,
        HibernateJpaApplication.class
})
public class HibernateJpaApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(HibernateJpaApplication.class, args);
    }
}
