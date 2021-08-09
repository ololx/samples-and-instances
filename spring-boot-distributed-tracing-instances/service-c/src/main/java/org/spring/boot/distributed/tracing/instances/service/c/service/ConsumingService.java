package org.spring.boot.distributed.tracing.instances.service.c.service;

/**
 * The interface Consuming service.
 *
 * @param <Q> the type parameter
 * @author Alexander A. Kropotin
 * @project service -c
 * @created 2021 -08-09 21:00 <p>
 */
public interface ConsumingService<Q> {

    /**
     * Receive.
     *
     * @param message the message
     * @throws Exception the exception
     */
    void receive(Q message) throws Exception;
}
