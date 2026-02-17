package net.jordimp.productoffers.shared.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Populate MDC with the incoming `x-correlator` header for the duration of the request. Ensures MDC
 * is cleared after the request completes so correlator is available to controller, service and
 * exception handlers during processing.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorrelatorMdcFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "x-correlator";
    public static final String MDC_KEY = "correlator";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String correlator = request.getHeader(HEADER_NAME);
        if (correlator != null && !correlator.isBlank()) {
            MDC.put(MDC_KEY, correlator);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_KEY);
        }
    }
}
