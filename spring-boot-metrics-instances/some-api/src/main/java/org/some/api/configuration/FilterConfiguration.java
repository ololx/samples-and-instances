package org.some.api.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * The type Filter configuration.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 2021 -07-03 11:49 <p>
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
