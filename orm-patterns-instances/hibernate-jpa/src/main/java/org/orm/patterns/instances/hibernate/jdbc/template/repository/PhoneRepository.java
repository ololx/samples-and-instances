package org.orm.patterns.instances.hibernate.jdbc.template.repository;

import org.orm.patterns.instances.hibernate.jdbc.template.model.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * The interface Phone repository.
 */
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    boolean existsById(@Param("id") Long id);
}