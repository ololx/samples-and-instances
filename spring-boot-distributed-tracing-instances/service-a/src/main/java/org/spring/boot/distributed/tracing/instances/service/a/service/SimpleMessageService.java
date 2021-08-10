package org.spring.boot.distributed.tracing.instances.service.a.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * The type Simple message service.
 *
 * @author Alexander A. Kropotin
 * @project service -a
 * @created 2021 -08-04 17:36 <p>
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("SimpleMessageService")
public class SimpleMessageService implements MessageService {

    @Qualifier("RestTemplateServiceBClient")
    ServiceBClient serviceBClient;

    @Override
    public ResponseEntity<JsonNode> create(JsonNode detail) throws Exception {
        log.debug("Send the request to service-a - {}", detail);
        ResponseEntity<JsonNode> response = this.serviceBClient.sendCreateRequest(detail);
        log.debug("Receive the response from service-a - {}", response);

        return response;
    }
}
