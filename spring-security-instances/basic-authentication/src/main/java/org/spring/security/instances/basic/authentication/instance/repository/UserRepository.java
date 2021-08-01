package org.spring.security.instances.basic.authentication.instance.repository;

import org.spring.security.instances.basic.authentication.instance.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User repository.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:21 <p>
 */
@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * Find first by name user.
     *
     * @param name the name
     * @return the user
     */
    Optional<User> findFirstByName(
            @Param("name") String name
    );

    /**
     * Find first by name and is active user.
     *
     * @param name     the name
     * @param isActive the is active
     * @return the user
     */
    User findFirstByNameAndIsActive(
            @Param("name") String name,
            @Param("isActive") Boolean isActive
    );

    /**
     * Find first by name and password and is active user.
     *
     * @param name     the name
     * @param password the password
     * @param isActive the is active
     * @return the user
     */
    Optional<User> findFirstByNameAndPasswordAndIsActive(
            @Param("name") String name,
            @Param("password") String password,
            @Param("isActive") Boolean isActive
    );
}
