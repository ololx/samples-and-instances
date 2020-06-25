package org.restful.querying.instances.specification.builder.repository;

import org.restful.querying.instances.specification.builder.model.entity.Item;
import org.restful.querying.instances.specification.builder.model.entity.QItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

/**
 * The interface Item repository.
 *
 * @author Alexander A. Kropotin
 * @project restful -querying-instances
 * @created 24.05.2020 07:54 <p>
 */
public interface ItemRepository extends
        QuerydslPredicateExecutor<Item>,
        QuerydslBinderCustomizer<QItem>,
        JpaRepository<Item, Long>,
        JpaSpecificationExecutor<Item> {

    @Override
    default void customize(QuerydslBindings bindings, QItem item) {
    }
}