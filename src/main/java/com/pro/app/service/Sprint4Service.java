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

    private final RestTemplate restTemplate = new RestTemplate();
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public void firstRequest(String userId, String serviceName) {

        // trace_id 생성
        String traceType = "request";
        String traceId = UUID.randomUUID().toString();

        MDC.put("trace_type", traceType);
        MDC.put("trace_id", traceId);
        MDC.put("user_id", userId);

        log.info("A Logic is started");

        // HTTP 헤더에 trace_id 포함
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Trace-Type", traceType);
        headers.set("X-Trace-Id", traceId);
        headers.set("X-User-Id", userId);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        log.info("A Logic is sent API to Other Service");

        for(int i=0 ; i<5 ; i++) {
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://"+ serviceName+"/second_log/"+i,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );
        }


        log.info("A Logic is completed");
    }

    public void secondRequest(Integer count) {
        count++;
        log.info("Application Logs for Trace : B Logic is progressing " + count + "/5");
    }
}


