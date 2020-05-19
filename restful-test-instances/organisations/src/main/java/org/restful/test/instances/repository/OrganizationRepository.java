package org.restful.test.instances.repository;

import org.restful.test.instances.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long>,
        JpaSpecificationExecutor<Organization> {
}