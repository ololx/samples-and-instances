package org.spring.boot.metrics.instances.some.api.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @project some-api
 * @created 2021-07-28 12:40
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("SimpleSomeDataExistenceService")
public class SimpleSomeDataExistenceService implements SomeDataExistenceService {

    ObjectMapper objectMapper;

    RabbitTemplate someDataExistenceProducer;

    @RabbitListener(queues = "some.data.existence.response")
    public void receive(byte[] message) throws IOException {
        log.info("Receive message - {}", message);
        JsonNode someDataDetail = this.objectMapper.readValue(message, JsonNode.class);
        log.debug("Read detail from message - {}", someDataDetail);
    }

    public void send(JsonNode someDataExistenceDetail) throws JsonProcessingException {
        log.info("Send message - {}", someDataExistenceDetail);
        this.someDataExistenceProducer.convertAndSend(
                "some.data.existence.request",
                objectMapper.writeValueAsBytes(someDataExistenceDetail)
        );
    }
}
