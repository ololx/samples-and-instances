package org.spring.boot.distributed.tracing.instances.service.a.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

/**
 * The interface Message service.
 *
 * @author Alexander A. Kropotin
 * @project service -a
 * @created 2021 -08-04 17:23 <p>
 */
public interface MessageService extends CreatingService<JsonNode, ResponseEntity<JsonNode>> {
}
