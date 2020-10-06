package org.orm.patterns.instances.hibernate.jpa.repository;

import org.orm.patterns.instances.hibernate.jpa.model.entity.Person;
import org.orm.patterns.instances.hibernate.jpa.model.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}