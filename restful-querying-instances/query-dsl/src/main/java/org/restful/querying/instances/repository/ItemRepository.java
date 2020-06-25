package org.restful.querying.instances.repository;

import org.restful.querying.instances.model.entity.Item;
import org.restful.querying.instances.model.entity.QItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ItemRepository extends
        QuerydslPredicateExecutor<Item>,
        QuerydslBinderCustomizer<QItem>,
        JpaRepository<Item, Long>,
        JpaSpecificationExecutor<Item> {

    @Override
    default void customize(QuerydslBindings bindings, QItem item) {
    }
}