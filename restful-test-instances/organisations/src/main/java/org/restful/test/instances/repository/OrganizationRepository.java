package org.restful.test.instances.repository;

import org.restful.test.instances.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @project restful-test-instances
 * @created 11.06.2020 10:07
 * <p>
 * @author Alexander A. Kropotin
 */
public interface OrganizationRepository extends JpaRepository<Organization, Long>,
        JpaSpecificationExecutor<Organization> {
}