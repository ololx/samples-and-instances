package org.some.api.commons.filtering;

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
@Component("LogFiltering")
public class LogFiltering extends OncePerRequestFilter {

    private static final String REQUEST_ID = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestId = request.getHeader(REQUEST_ID);
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }

        MDC.put(REQUEST_ID, requestId);

        try {
            log.trace("Started process request with {} : {}", REQUEST_ID, requestId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}