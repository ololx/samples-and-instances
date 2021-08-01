package org.spring.security.instances.basic.authentication.instance.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.security.instances.basic.authentication.instance.core.CustomAuthenticationProvider;
import org.spring.security.instances.basic.authentication.instance.core.NoPopupBasicAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The type Security configuration.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:44 <p>
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] IGNORE_LIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Qualifier("CustomAuthenticationProvider")
    CustomAuthenticationProvider authProvider;

    /**
     * Config authentication.
     *
     * @param auth the auth
     * @throws Exception the exception
     */
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf()
                .disable()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/**").permitAll()
                .antMatchers("/authentication/info").authenticated()
                .antMatchers(
                        "/users/",
                        "/users/**"
                ).hasAnyAuthority("ADMIN", "USER")
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint());

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(IGNORE_LIST);
    }
}
