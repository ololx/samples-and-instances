package org.orm.patterns.instances.hibernate.jpa.repository;

import org.orm.patterns.instances.hibernate.jpa.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}