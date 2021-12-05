package org.innopolis.university.java.team.spring.boot.custom.message.converting.instances.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @project spring-boot-custom-message-converting-instances
 * @created 2021-12-05 16:20
 * <p>
 * @author Alexander A. Kropotin
 */
@Configuration
public class FilterConfiguration {

    /**
     * Gets filter registration bean.
     *
     * @return the filter registration bean
     */
    @Bean(name = "filterRegistrationBean")
    public FilterRegistrationBean getFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);

        return registrationBean;
    }

}

