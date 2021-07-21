package org.spring.boot.metrics.instances.some.api.client.service;

/**
 * The interface Create service.
 *
 * @param <S> the type parameter
 * @param <Q> the type parameter
 * @author Alexander A. Kropotin
 * @project some -api-client
 * @created 2021 -07-21 20:19 <p>
 */
public interface CreateService<S, Q> {

    /**
     * Create s.
     *
     * @param detail the detail
     * @return the s
     */
    S create(Q detail);
}
