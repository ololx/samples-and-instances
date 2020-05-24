package org.restful.querying.instances.repository;

import org.restful.querying.instances.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemRepository extends
        QuerydslPredicateExecutor<Item>,
        JpaRepository<Item, Long>,
        JpaSpecificationExecutor<Item> {
}