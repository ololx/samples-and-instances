package org.restful.updating.instances.option.field.approach.repository;

import org.restful.updating.instances.option.field.approach.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @project restful-updating-instances
 * @created 28.06.2020 07:54
 * <p>
 * @author Alexander A. Kropotin
 */
public interface PersonRepository extends
        JpaRepository<Person, Long>,
        JpaSpecificationExecutor<Person> {
}