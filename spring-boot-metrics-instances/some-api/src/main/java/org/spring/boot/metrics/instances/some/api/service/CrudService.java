package org.spring.boot.metrics.instances.some.api.service;

import org.spring.boot.metrics.instances.some.api.commons.mapping.MapperAdapter;
import org.spring.boot.metrics.instances.some.api.model.detail.SomeDataDetail;

/**
 * The interface Crud service.
 *
 * @param <DETAIL> the type parameter
 * @param <ID>     the type parameter
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 2021 -07-06 17:59 <p>
 */
public interface CrudService<DETAIL extends SomeDataDetail, ID> {

    /**
     * Create detail.
     *
     * @param detail the detail
     * @return the detail
     * @throws MappingException the mapping exception
     */
    DETAIL create(DETAIL detail) throws MapperAdapter.MappingException;

    /**
     * Read detail.
     *
     * @param id the id
     * @return the detail
     * @throws MappingException the mapping exception
     */
    DETAIL read(ID id) throws MapperAdapter.MappingException;

    /**
     * Update detail.
     *
     * @param id     the id
     * @param detail the detail
     * @return the detail
     * @throws MappingException the mapping exception
     */
    DETAIL update(ID id, DETAIL detail) throws MapperAdapter.MappingException;

    /**
     * Delete detail.
     *
     * @param id the id
     * @return the detail
     */
    DETAIL delete(ID id);
}
