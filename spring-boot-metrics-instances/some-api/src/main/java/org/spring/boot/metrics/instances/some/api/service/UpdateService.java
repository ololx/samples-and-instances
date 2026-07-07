package org.spring.boot.metrics.instances.some.api.service;

import org.spring.boot.metrics.instances.some.api.commons.mapping.MapperAdapter;
import org.spring.boot.metrics.instances.some.api.model.detail.SomeDataDetail;

/**
 * @project some-api
 * @created 2021-07-21 19:58
 * <p>
 * @author Alexander A. Kropotin
 */
public interface UpdateService<RESPONSE_DETAIL extends SomeDataDetail, REQUEST_DETAIL extends SomeDataDetail, REQUEST_ID> {

    /**
     * Update detail.
     *
     * @param id     the id
     * @param detail the detail
     * @return the detail
     * @throws MapperAdapter.MappingException the mapping exception
     */
    RESPONSE_DETAIL update(REQUEST_ID id, REQUEST_DETAIL detail) throws MapperAdapter.MappingException;
}
