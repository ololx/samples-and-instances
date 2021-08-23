package org.spring.boot.metrics.instances.some.api.service;

import org.spring.boot.metrics.instances.some.api.commons.mapping.MapperAdapter;
import org.spring.boot.metrics.instances.some.api.model.detail.SomeDataDetail;

/**
 * @project some-api
 * @created 2021-07-21 19:58
 * <p>
 * @author Alexander A. Kropotin
 */
public interface RetrieveService<RESPONSE_DETAIL extends SomeDataDetail,  REQUEST_ID> {

    /**
     * Read detail.
     *
     * @param id the id
     * @return the detail
     * @throws MapperAdapter.MappingException the mapping exception
     */
    RESPONSE_DETAIL retrieve(REQUEST_ID id) throws MapperAdapter.MappingException;
}
