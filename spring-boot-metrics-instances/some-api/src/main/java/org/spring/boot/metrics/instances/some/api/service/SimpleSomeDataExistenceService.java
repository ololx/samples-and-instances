package org.spring.boot.metrics.instances.some.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.boot.metrics.instances.some.api.model.detail.SomeDataDetail;
import org.spring.boot.metrics.instances.some.api.model.detail.SomeDataExistenceDetail;
import org.spring.boot.metrics.instances.some.api.repository.SomeDataRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("SomeDataRepository")
    SomeDataRepository repository;

    ObjectMapper objectMapper;

    RabbitTemplate someDataExistenceProducer;

    @RabbitListener(queues = "some.data.existence.request")
    public void receive(byte[] message) throws IOException {
        log.info("Receive message - {}", message);
        SomeDataDetail someDataDetail = this.objectMapper.readValue(message, SomeDataDetail.class);
        log.debug("Read detail from message - {}", someDataDetail);

        Integer id = someDataDetail.getId() != null ? someDataDetail.getId().get() : null;
        boolean existence = false;
        if (id != null) {
            existence = this.repository.existsById(id);
        }
        log.debug("Check existence - {}", existence);

        SomeDataExistenceDetail someDataExistenceDetail = SomeDataExistenceDetail.builder()
                .id(id)
                .existence(existence)
                .build();

        this.send(someDataExistenceDetail);
    }

    public void send(SomeDataExistenceDetail someDataExistenceDetail) throws JsonProcessingException {
        log.info("Send message - {}", someDataExistenceDetail);
        this.someDataExistenceProducer.convertAndSend(
                "some.data.existence.response",
                objectMapper.writeValueAsBytes(someDataExistenceDetail)
        );
    }
}
