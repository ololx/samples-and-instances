package org.change.data.capture.instances.embedded.debezium.service;

import io.debezium.data.Envelope;

import java.util.Map;

/**
 * The interface Replication service.
 *
 * @author Alexander A. Kropotin
 * @project embeded -debezium
 * @created 2021 -10-03 20:37 <p>
 */
public interface ReplicationService {

    String database();

    String table();

    /**
     * Replicate.
     *
     * @param payload   the payload
     * @param operation the operation
     */
    void replicate(Map<String, Object> payload, Envelope.Operation operation);
}
