package org.spring.boot.distributed.tracing.instances.service.b.repository;

import org.spring.boot.distributed.tracing.instances.service.b.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Message repository.
 *
 * @author Alexander A. Kropotin
 * @project service -b
 * @created 2021 -08-04 12:32 <p>
 */
@Repository("MessageRepository")
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
