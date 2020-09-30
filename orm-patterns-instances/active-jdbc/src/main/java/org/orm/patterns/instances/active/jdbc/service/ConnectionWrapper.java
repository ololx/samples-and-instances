package org.orm.patterns.instances.active.jdbc.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type Connection wrapper.
 *
 * @author Alexander A. Kropotin
 * @project orm -patterns-instances
 * @created 30.09.2020 14:05 <p>
 */
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Service
public class ConnectionWrapper {

    /**
     * The Driver.
     */
    @Value("${spring.datasource.driver}")
    String driver;

    /**
     * The Url.
     */
    @Value("${spring.datasource.url}")
    String url;

    /**
     * The Username.
     */
    @Value("${spring.datasource.username}")
    String username;

    /**
     * The Password.
     */
    @Value("${spring.datasource.password}")
    String password;


    /**
     * Open.
     */
    public void open() {
        Base.open(driver, url, username, password);
    }

    /**
     * Close.
     */
    public void close() {
        Base.close();
    }
}
