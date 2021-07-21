package org.spring.boot.metrics.instances.some.api.client.service;

/**
 * The interface Update service.
 *
 * @param <S>  the type parameter
 * @param <Q>  the type parameter
 * @param <ID> the type parameter
 * @author Alexander A. Kropotin
 * @project some -api-client
 * @created 2021 -07-21 20:19 <p>
 */
public interface UpdateService<S, Q, ID> {

    /**
     * Update s.
     *
     * @param id     the id
     * @param detail the detail
     * @return the s
     */
    S update(ID id, Q detail);
}
