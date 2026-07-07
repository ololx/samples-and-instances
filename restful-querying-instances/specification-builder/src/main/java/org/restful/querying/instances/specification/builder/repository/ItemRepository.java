package org.restful.querying.instances.specification.builder.repository;

import org.restful.querying.instances.specification.builder.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @project restful-querying-instances
 * @created 24.05.2020 07:54
 * <p>
 * @author Alexander A. Kropotin
 */
public interface ItemRepository extends
        JpaRepository<Item, Long>,
        JpaSpecificationExecutor<Item> {
}