package org.change.data.capture.instances.embedded.debezium.repository;

import org.change.data.capture.instances.embedded.debezium.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Person repository.
 *
 * @author Alexander A. Kropotin
 * @project embeded -debezium
 * @created 2021 -09-08 15:01 <p>
 */
@Repository("DepartmentRepository")
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    /**
     * Exists by code boolean.
     *
     * @param code the code
     * @return the boolean
     */
    boolean existsByCode(String code);

    /**
     * Delete by code.
     *
     * @param code the code
     */
    void deleteByCode(String code);
}