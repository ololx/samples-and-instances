package org.spring.boot.distributed.tracing.instances.service.c.commons.filtering;

import io.opentracing.Span;
import io.opentracing.contrib.spring.rabbitmq.RabbitMqSpanDecorator;
import org.slf4j.MDC;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

/**
 * The type On receive tracing filter.
 *
 * @author Alexander A. Kropotin
 * @project service -c
 * @created 2021 -08-09 20:43 <p>
 */
@Component("OnReceiveTracingFilter")
public class OnReceiveTracingFilter extends RabbitMqSpanDecorator {

    @Override
    public void onReceive(MessageProperties messageProperties, Span span) {
        super.onReceive(messageProperties, span);

        MDC.put("traceId", span.context().toTraceId());
        MDC.put("spanId", span.context().toSpanId());
    }
}
