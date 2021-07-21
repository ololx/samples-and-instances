package org.spring.boot.metrics.instances.some.api.client.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * The type Simple some api client service.
 *
 * @author Alexander A. Kropotin
 * @project some -api-client
 * @created 2021 -07-21 20:43 <p>
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("SimpleSomeApiClientService")
public class SimpleSomeApiClientService implements SomeApiClientService {

    /**
     * The type Some api resource.
     */
    @Slf4j
    @RequiredArgsConstructor
    @FieldDefaults(
            level = AccessLevel.PRIVATE
    )
    @Component("SimpleSomeApiClientServiceConfiguration")
    public static class SomeApiResource {

        @Value("${client.some-api}")
        String uri;

        /**
         * Get uri.
         *
         * @return the uri
         */
        public URI get() {
            URI requestUri = UriComponentsBuilder.fromUriString(this.uri)
                    .build()
                    .toUri();
            log.trace("Create URI - {}", requestUri);

            return requestUri;
        }

        /**
         * Get uri.
         *
         * @param id the id
         * @return the uri
         */
        public URI get(Integer id) {
            URI requestUri = UriComponentsBuilder.fromUriString(this.uri + "{id}")
                    .build(id);
            log.trace("Create URI - {}", requestUri);

            return requestUri;
        }
    }

    @Qualifier("SimpleSomeApiClientServiceConfiguration")
    SomeApiResource someApiResource;

    RestTemplate someApiClient;

    @Override
    public ResponseEntity<JsonNode> create(JsonNode someDataInstance) {
        URI requestUri = this.someApiResource.get();
        log.debug("Create POST URI - {}", requestUri);

        RequestEntity<JsonNode> requestEntity = RequestEntity.post(requestUri)
                .body(someDataInstance);
        log.debug("Create POST request entity - {}", requestEntity);

        ResponseEntity<JsonNode> responseEntity = this.someApiClient.exchange(requestEntity, JsonNode.class);
        log.debug("Receive POST response entity - {}", responseEntity);

        return responseEntity;
    }

    @Override
    public ResponseEntity<JsonNode> delete(Integer id) {
        URI requestUri = this.someApiResource.get(id);
        log.debug("Create DELETE URI - {}", requestUri);

        RequestEntity<Void> requestEntity = RequestEntity.delete(requestUri)
                .build();
        log.debug("Create DELETE request entity - {}", requestEntity);

        ResponseEntity<JsonNode> responseEntity = this.someApiClient.exchange(requestEntity, JsonNode.class);
        log.debug("Receive DELETE response entity - {}", responseEntity);

        return responseEntity;
    }

    @Override
    public ResponseEntity<JsonNode> retrieve(Integer id) {
        URI requestUri = this.someApiResource.get(id);
        log.debug("Create GET URI - {}", requestUri);

        RequestEntity<Void> requestEntity = RequestEntity.get(requestUri)
                .build();
        log.debug("Create GET request entity - {}", requestEntity);

        ResponseEntity<JsonNode> responseEntity = this.someApiClient.exchange(requestEntity, JsonNode.class);
        log.debug("Receive GET response entity - {}", responseEntity);

        return responseEntity;
    }

    @Override
    public ResponseEntity<JsonNode> update(Integer id, JsonNode someDataInstance) {
        URI requestUri = this.someApiResource.get(id);
        log.debug("Create PATCH URI - {}", requestUri);

        RequestEntity<JsonNode> requestEntity = RequestEntity.patch(requestUri)
                .body(someDataInstance);
        log.debug("Create PATCH request entity - {}", requestEntity);

        ResponseEntity<JsonNode> responseEntity = this.someApiClient.exchange(requestEntity, JsonNode.class);
        log.debug("Receive PATCH response entity - {}", responseEntity);

        return responseEntity;
    }
}
