package org.restful.querying.instances.specification.builder.repository;

import org.restful.querying.instances.specification.builder.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository extends
        JpaRepository<Item, Long>,
        JpaSpecificationExecutor<Item> {
}