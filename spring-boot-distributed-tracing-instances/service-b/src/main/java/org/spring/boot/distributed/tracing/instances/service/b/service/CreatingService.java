package org.spring.boot.distributed.tracing.instances.service.b.service;

/**
 * The interface Creating service.
 *
 * @param <Q> the type parameter
 * @param <S> the type parameter
 * @author Alexander A. Kropotin
 * @project service -b
 * @created 2021 -08-04 15:19 <p>
 */
public interface CreatingService<Q, S> {

    /**
     * Create s.
     *
     * @param detail the detail
     * @return the s
     * @throws Exception the exception
     */
    S create(Q detail) throws Exception;
}
