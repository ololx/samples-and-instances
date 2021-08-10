package org.spring.boot.distributed.tracing.instances.service.b.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * The type Message.
 *
 * @author Alexander A. Kropotin
 * @project service -b
 * @created 2021 -08-04 12:14 <p>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        of = {
                "id"
        },
        doNotUseGetters = true
)
@Data
@FieldDefaults(
        level = AccessLevel.PRIVATE
)
@Entity(name = "Message")
@Table(name = "message")
public final class Message implements ServiceBEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_pkey"
    )
    @SequenceGenerator(
            name = "message_pkey",
            sequenceName = "message_id_seq",
            allocationSize = 1
    )
    @Column(
            name = "id",
            insertable = false,
            nullable = false
    )
    Integer id;

    @Column(
            name = "content",
            nullable = false
    )
    String content;
}
