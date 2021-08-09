package org.spring.boot.distributed.tracing.instances.service.c.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * The type Simple message consuming service.
 *
 * @author Alexander A. Kropotin
 * @project service -c
 * @created 2021 -08-09 21:01 <p>
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Component("SimpleMessageConsumingComponent")
public class SimpleMessageConsumingComponent implements ConsumingComponent<byte[]> {

    @Qualifier("ObjectMapper")
    ObjectMapper messageMapper;

    @RabbitListener(queues = "message.request")
    @Override
    public void receive(byte[] message) throws Exception {
        log.info("Receive message - {}", message);
        JsonNode messageDetail = this.messageMapper.readTree(message);
        log.debug("Read detail from message - {}", messageDetail);
    }
}
