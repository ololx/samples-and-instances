package org.some.api.repository;

import org.some.api.model.entity.SomeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Some data repository.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 2021 -07-06 17:23 <p>
 */
@Repository("SomeDataRepository")
public interface SomeDataRepository extends JpaRepository<SomeData, Integer> {
}
