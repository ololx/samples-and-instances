package org.spring.boot.distributed.tracing.instances.service.b.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.boot.distributed.tracing.instances.service.b.commons.mapping.MapperAdapter;
import org.spring.boot.distributed.tracing.instances.service.b.model.detail.MessageDetail;
import org.spring.boot.distributed.tracing.instances.service.b.repository.MessageRepository;
import org.spring.boot.distributed.tracing.instances.service.b.model.entity.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Simple message service.
 *
 * @author Alexander A. Kropotin
 * @project service -b
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

    @Qualifier("ObjectMapperAdapter")
    MapperAdapter mapperAdapter;

    @Qualifier("MessageRepository")
    MessageRepository messageRepository;

    @Qualifier("SimpleMessageProducingComponent")
    ProducingComponent messageProducingComponent;

    @Override
    public MessageDetail create(MessageDetail detail) throws Exception {
        Message newEntity = this.mapperAdapter.map(detail, Message.class);
        log.debug("Map detail into newEntity - {}", detail);

        this.messageRepository.save(newEntity);
        log.debug("Create the newEntity - {}", newEntity);

        detail.setId(newEntity.getId());

        this.messageProducingComponent.send(detail);

        return detail;
    }
}
