package org.orm.patterns.instances.hibernate.jdbc.template.model.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The interface Data acess object.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 * @author Alexander A. Kropotin
 * @project orm -patterns-instances
 * @created 12.05.2021 08:00 <p>
 */
public interface JdbcTemplateRepository<T, ID> {

    /**
     * Save s.
     *
     * @param <S>    the type parameter
     * @param entity the entity
     * @return the s
     */
    <S extends T> S save(S entity);

    /**
     * Save all list.
     *
     * @param <S>      the type parameter
     * @param entities the entities
     * @return the list
     */
    <S extends T> Collection<S> saveAll(Collection<S> entities);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(ID id);

    /**
     * Find all iterable.
     *
     * @return the iterable
     */
    List<T> findAll();

    /**
     * Delete.
     *
     * @param entity the entity
     */
    void delete(T entity);
}
