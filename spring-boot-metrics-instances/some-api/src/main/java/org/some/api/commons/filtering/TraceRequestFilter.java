package org.some.api.commons.filtering;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @project some-api
 * @created 2021-07-09 15:37
 * <p>
 * @author Alexander A. Kropotin
 */
@Slf4j
@Component("TraceRequestFilter")
public class TraceRequestFilter extends OncePerRequestFilter {

    private static final String TRACE_ID = "traceId";

    private static final String SPAN_ID = "spanId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Span span = GlobalTracer.get().activeSpan();
        if (span == null) {
            span = GlobalTracer.get().buildSpan(request.getMethod()).start();
        }

        String spanId = span.context().toSpanId();

        String traceId = null;
        if ((traceId = request.getHeader(TRACE_ID)) == null) {
            traceId = span.context().toTraceId();
        }

        MDC.put(TRACE_ID, traceId);
        MDC.put(SPAN_ID, spanId);

        try {
            log.trace("Started process request with {} : {} && {} : {}", TRACE_ID, traceId, SPAN_ID, spanId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}