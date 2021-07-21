package org.spring.boot.metrics.instances.some.api.service;

import org.spring.boot.metrics.instances.some.api.model.detail.SomeDataDetail;

/**
 * @project some-api
 * @created 2021-07-21 19:58
 * <p>
 * @author Alexander A. Kropotin
 */
public interface DeleteService<RESPONSE_DETAIL extends SomeDataDetail, REQUEST_ID> {

    /**
     * Delete detail.
     *
     * @param id the id
     * @return the detail
     */
    RESPONSE_DETAIL delete(REQUEST_ID id);
}
