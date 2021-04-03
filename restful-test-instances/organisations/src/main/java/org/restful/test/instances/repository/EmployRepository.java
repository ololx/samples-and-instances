package org.restful.test.instances.repository;

import org.restful.test.instances.model.entity.Employ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @project restful-test-instances
 * @created 03.04.2021 09:19
 * <p>
 * @author Alexander A. Kropotin
 */
public interface EmployRepository extends JpaRepository<Employ, Long>,
        JpaSpecificationExecutor<Employ> {
}