package org.spring.boot.distributed.tracing.instances.service.b.service;

/**
 * The interface Producing service.
 *
 * @param <Q> the type parameter
 * @author Alexander A. Kropotin
 * @project service -b
 * @created 2021 -08-09 21:08 <p>
 */
public interface ProducingService <Q> {

    /**
     * Send.
     *
     * @param message the message
     * @throws Exception the exception
     */
    void send(Q message) throws Exception;
}
