package org.some.api.commons.filtering;

import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The type Tracing filter.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 2021 -07-09 15:37 <p>
 */
@Slf4j
@FieldDefaults(
        level = AccessLevel.PROTECTED,
        makeFinal = true
)
@Component("TracingFilter")
public abstract class TracingFilter extends OncePerRequestFilter {

    /**
     * The Trace id.
     */
    static String TRACE_ID = "traceId";

    /**
     * The Span id.
     */
    static String SPAN_ID = "spanId";

    /**
     * Gets span.
     *
     * @return the span
     */
    protected Span getSpan() {
        Span span = GlobalTracer.get().activeSpan();
        if (span == null) {
            span = GlobalTracer.get().buildSpan(this.getFilterName()).start();
        }

        return span;
    }
}