package com.pro.app.component;

import org.slf4j.MDC;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

@WebFilter("/*")
class TraceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException { // 예외 선언 추가
        try {
            MDC.put("trace_id", UUID.randomUUID().toString());
            MDC.put("span_id", UUID.randomUUID().toString());
            chain.doFilter(request, response); // 필터 체인 실행
        } finally {
            MDC.clear(); // MDC 값 초기화 (메모리 누수 방지)
        }
    }
}