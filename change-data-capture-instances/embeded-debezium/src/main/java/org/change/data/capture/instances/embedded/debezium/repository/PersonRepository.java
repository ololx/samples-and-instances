package org.change.data.capture.instances.embedded.debezium.repository;

import org.change.data.capture.instances.embedded.debezium.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Person repository.
 *
 * @author Alexander A. Kropotin
 * @project embeded -debezium
 * @created 2021 -09-08 15:01 <p>
 */
@Repository("PersonRepositpry")
public interface PersonRepository extends JpaRepository<Person, Long> {
}