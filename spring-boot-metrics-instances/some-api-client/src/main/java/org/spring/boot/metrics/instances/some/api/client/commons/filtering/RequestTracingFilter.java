package org.spring.boot.metrics.instances.some.api.client.commons.filtering;

import io.opentracing.Span;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Request tracing filter.
 *
 * @author Alexander A. Kropotin
 * @project some -api -client
 * @created 2021 -07-09 15:37 <p>
 */
@Slf4j
@Component("RequestTracingFilter")
public class RequestTracingFilter extends TracingFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Span span = this.getSpan();
        String spanId = span.context().toSpanId();

        String traceId = null;
        if ((traceId = request.getHeader(TRACE_ID)) == null) {
            traceId = span.context().toTraceId();
        }

        MDC.put(TRACE_ID, traceId);
        MDC.put(SPAN_ID, spanId);

        try {
            log.trace("Start the process request with {} : {} && {} : {}", TRACE_ID, traceId, SPAN_ID, spanId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}