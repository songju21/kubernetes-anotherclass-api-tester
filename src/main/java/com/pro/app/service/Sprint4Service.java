package com.pro.app.service;


import org.slf4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import java.util.UUID;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

@Service
public class Sprint4Service {
    private final RestTemplate restTemplate;

    public Sprint4Service(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public void firstRequest(String applicationCode, String serviceName, String userId) {

        // trace_id 생성
        String traceId = UUID.randomUUID().toString();
        MDC.put("trace_id", traceId);
        MDC.put("application_code", applicationCode);
        MDC.put("user_id", userId);
        log.info("Processing user request");
        MDC.clear();

        // HTTP 헤더에 trace_id 포함
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Trace-Id", traceId);
        headers.set("X-User-Id", userId);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://"+ serviceName+"/second_log",
                HttpMethod.GET,
                requestEntity,
                String.class
        );


    }

    public void secondRequest(String traceId, String applicationCode, String userId) {
        MDC.put("trace_id", traceId);
        MDC.put("application_code", applicationCode);
        MDC.put("user_id", userId);

        log.info("Processing user request");
        MDC.clear();
    }
}


