package org.restful.querying.instances.repository;

import org.restful.querying.instances.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ItemRepository extends
        JpaRepository<Item, Long>,
        JpaSpecificationExecutor<Item> {

    Page<Item> findByIdIn(List<Long> id);
}