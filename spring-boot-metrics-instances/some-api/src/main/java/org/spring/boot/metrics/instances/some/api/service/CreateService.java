package org.spring.boot.metrics.instances.some.api.service;

import org.spring.boot.metrics.instances.some.api.commons.mapping.MapperAdapter;
import org.spring.boot.metrics.instances.some.api.model.detail.SomeDataDetail;

/**
 * @project some-api
 * @created 2021-07-21 19:58
 * <p>
 * @author Alexander A. Kropotin
 */
public interface CreateService<RESPONSE_DETAIL extends SomeDataDetail, REQUEST_DETAIL extends SomeDataDetail> {

    /**
     * Create detail.
     *
     * @param detail the detail
     * @return the detail
     * @throws MapperAdapter.MappingException the mapping exception
     */
    RESPONSE_DETAIL create(REQUEST_DETAIL detail) throws MapperAdapter.MappingException;
}
