package org.spring.boot.distributed.tracing.instances.service.a.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * The type Rest template configuration.
 *
 * @author Alexander A. Kropotin
 * @project service -a
 * @created 2021 -08-08 20:52 <p>
 */
@Configuration
public class RestTemplateConfiguration {

    /**
     * The type Http client response exception.
     */
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(
            level = AccessLevel.PRIVATE
    )
    public static class HttpClientResponseException extends IOException {

        /**
         * The Status.
         */
        HttpStatus status;

        /**
         * The Timestamp.
         */
        Date timestamp;

        /**
         * The Stack trace.
         */
        String stackTraces;

        /**
         * The Comment.
         */
        String comment;

        /**
         * The Message.
         */
        String message;

        /**
         * The Details.
         */
        String details;

        {
            this.timestamp = new Date();
        }
    }

    /**
     * The type Client exception detail handler.
     */
    @Slf4j
    public static class ClientExceptionDetailHandler extends DefaultResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            int rawStatusCode = response.getRawStatusCode();

            return super.hasError(response) && rawStatusCode != 404;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            log.trace("Receive HTTP response - {}", response.toString());

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
                String httpBodyResponse = reader.lines().collect(Collectors.joining(""));
                log.trace("Extract exception detail from response - {}", httpBodyResponse);

                HttpClientResponseException e =  new ObjectMapper().readValue(httpBodyResponse, HttpClientResponseException.class);
                log.trace("Create exception - " + e.toString());

                throw e;
            }
        }
    }

    /**
     * Rest template rest template.
     *
     * @param builder the builder
     * @return the rest template
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(new ClientExceptionDetailHandler())
                .setConnectTimeout(Duration.ofMillis(3000000))
                .setReadTimeout(Duration.ofMillis(3000000))
                .build();
    }
}

