package org.spring.boot.metrics.instances.some.api.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * The interface Delete service.
 *
 * @param <S>  the type parameter
 * @param <ID> the type parameter
 * @author Alexander A. Kropotin
 * @project some -api-client
 * @created 2021 -07-21 20:19 <p>
 */
public interface DeleteService<S, ID> {

    /**
     * Delete s.
     *
     * @param id the id
     * @return the s
     */
    S delete(ID id) throws Exception;
}
