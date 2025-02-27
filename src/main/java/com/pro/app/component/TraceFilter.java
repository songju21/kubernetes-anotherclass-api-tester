package com.pro.app.component;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;


import java.io.IOException;
import java.util.UUID;

@Component
@WebFilter("/*")
class TraceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String traceId = httpRequest.getHeader("X-Trace-Id");
        String traceType = httpRequest.getHeader("X-Trace-Type");
        String userId = httpRequest.getHeader("X-User-Id");



        if (traceType == null || traceType.isEmpty()) {
            traceType = "none";
        }
        if (traceId == null || traceId.isEmpty()) {
            traceId = "none";
        }
        if (userId == null || userId.isEmpty()) {
            userId = "none";
        }

        MDC.put("trace_type", traceType);
        MDC.put("trace_id", traceId);
        MDC.put("user_id", userId);

        chain.doFilter(request, response); // 필터 체인 실행

    }
}