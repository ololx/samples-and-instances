package org.spring.boot.distributed.tracing.instances.service.a.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * The type Rest template service b client.
 *
 * @author Alexander A. Kropotin
 * @project service -a
 * @created 2021 -08-08 20:28 <p>
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Component("RestTemplateServiceBClient")
public class RestTemplateServiceBClient implements ServiceBClient {


    @Value("${client.service-b}")
    @NonFinal
    String clientURI;

    @Qualifier("RestTemplate")
    RestTemplate someApiClient;

    @Override
    public ResponseEntity<JsonNode> sendCreateRequest(JsonNode request) {
        URI requestUri = UriComponentsBuilder.fromUriString(this.clientURI)
                .build()
                .toUri();
        log.trace("Create POST URI - {}", requestUri);

        RequestEntity<JsonNode> requestEntity = RequestEntity.post(requestUri)
                .body(request);
        log.trace("Create POST request entity - {}", requestEntity);

        ResponseEntity<JsonNode> responseEntity = this.someApiClient.exchange(requestEntity, JsonNode.class);
        log.trace("Receive POST response entity - {}", responseEntity);

        return responseEntity;
    }
}
