package org.spring.boot.metrics.instances.some.api.commons.filtering;

import io.opentracing.Span;
import io.opentracing.contrib.spring.rabbitmq.RabbitMqSpanDecorator;
import org.slf4j.MDC;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

/**
 * The type On receive tracing filter.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 2021 -07-28 17:23 <p>
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
