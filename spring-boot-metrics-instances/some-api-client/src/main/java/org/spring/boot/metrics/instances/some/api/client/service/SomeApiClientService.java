package org.spring.boot.metrics.instances.some.api.client.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

/**
 * The interface Some api client service.
 *
 * @author Alexander A. Kropotin
 * @project some -api-client
 * @created 2021 -07-21 20:41 <p>
 */
public interface SomeApiClientService extends CreateService<ResponseEntity<JsonNode>, JsonNode>,
        RetrieveService<ResponseEntity<JsonNode>, Integer>,
        UpdateService<ResponseEntity<JsonNode>, JsonNode, Integer>,
        DeleteService<ResponseEntity<JsonNode>, Integer> {
}
