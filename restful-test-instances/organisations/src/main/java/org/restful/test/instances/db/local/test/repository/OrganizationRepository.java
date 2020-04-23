package org.restful.test.instances.db.local.test.repository;

import org.restful.test.instances.db.local.test.model.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long>,
        JpaSpecificationExecutor<Organization> {

    //SELECT * FROM organization WHERE inn IS NOT NULL;
    List<Organization> findAllByInnIsNotNull();
}