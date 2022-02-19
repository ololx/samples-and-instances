package io.github.innopolis.university.java.team.book.api.base.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * project well-log
 * created 2021-11-28 18:33
 *
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
