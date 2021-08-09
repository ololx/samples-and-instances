package org.spring.boot.distributed.tracing.instances.service.b.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.boot.distributed.tracing.instances.service.b.model.entity.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @project service-b
 * @created 2021-08-09 21:09
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Service("SimpleMessageProducingService")
public class SimpleMessageProducingService implements ProducingService<Message> {

    @Qualifier("ObjectMapper")
    ObjectMapper messageMapper;

    @Qualifier("RabbitTemplate")
    RabbitTemplate messageProducer;

    @Override
    public void send(Message message) throws Exception {
        log.info("Send message - {}", message);
        this.messageProducer.convertAndSend(
                "some.data.existence.response",
                this.messageMapper.writeValueAsBytes(message)
        );
    }
}
