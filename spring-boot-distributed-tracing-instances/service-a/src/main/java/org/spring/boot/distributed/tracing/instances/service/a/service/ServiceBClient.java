package org.spring.boot.distributed.tracing.instances.service.a.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

/**
 * The interface Service b client.
 *
 * @author Alexander A. Kropotin
 * @project service -a
 * @created 2021 -08-08 20:26 <p>
 */
public interface ServiceBClient {

    /**
     * Send create request response entity.
     *
     * @param request the request
     * @return the response entity
     */
    ResponseEntity<JsonNode> sendCreateRequest(JsonNode request);
}
