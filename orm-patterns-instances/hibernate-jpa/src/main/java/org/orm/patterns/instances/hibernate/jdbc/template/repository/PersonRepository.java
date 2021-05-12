package org.orm.patterns.instances.hibernate.jdbc.template.repository;

import org.orm.patterns.instances.hibernate.jdbc.template.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Person repository.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}