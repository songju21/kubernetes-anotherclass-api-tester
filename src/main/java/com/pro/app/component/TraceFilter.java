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

    @Value(value = "${application.code}")
    private String applicationCode;

    @Value(value = "${downward.env.pod-name}")
    private String downwardApiEnvPodName;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request; // ✅ HttpServletRequest로 캐스팅
        String userId = httpRequest.getHeader("X-User-Id");
        String traceId = httpRequest.getHeader("X-Trace-Id");

        if (traceId == null || traceId.isEmpty()) {
            traceId = "none";
        }
        if (userId == null || userId.isEmpty()) {
            userId = "anonymous";
        }

        MDC.put("trace_id", traceId);
        MDC.put("application_code", applicationCode);
        MDC.put("pod_name", downwardApiEnvPodName);
        MDC.put("user_id", userId);

        chain.doFilter(request, response); // 필터 체인 실행

    }
}