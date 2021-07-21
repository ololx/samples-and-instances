package org.spring.boot.metrics.instances.some.api.client.service;

/**
 * The interface Retrieve service.
 *
 * @param <S>  the type parameter
 * @param <ID> the type parameter
 * @author Alexander A. Kropotin
 * @project some -api-client
 * @created 2021 -07-21 20:21 <p>
 */
public interface RetrieveService<S, ID> {

    /**
     * Retrieve s.
     *
     * @param id the id
     * @return the s
     */
    S retrieve(ID id);
}
