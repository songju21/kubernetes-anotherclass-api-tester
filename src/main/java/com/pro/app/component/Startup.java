package com.pro.app.component;

import com.pro.app.service.DefaultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Startup implements
        ApplicationListener<ContextRefreshedEvent> {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value(value = "${application.code}")
    private String applicationCode;

    @Value(value = "${downward.env.pod-name}")
    private String downwardApiEnvPodName;

    @Autowired
    private DefaultService defaultService;

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {

        System.out.println("applicationCode = " + applicationCode); // ✅ 값 확인
        System.out.println("downwardApiEnvPodName = " + downwardApiEnvPodName); // ✅ 값 확인

        MDC.put("trace_id", "none");
        MDC.put("application_code", applicationCode);
        MDC.put("pod_name", downwardApiEnvPodName);
        MDC.put("user_id", "anonymous");


        try {
            log.info("[System] App is initializing");
            Thread.sleep(1*1000);
            log.info("[System] Database is connecting");
            Thread.sleep(1*1000);
            log.info("[System] Database is connected");
            Thread.sleep(1*1000);
            log.info("[System] App is starting");
            Thread.sleep(1*1000);
            log.info("[System] App is started");
            Thread.sleep(1*1000);
            defaultService.isAppLive = true;
            Thread.sleep(1*1000);
            log.info("[System] ConfigMap data is loading..");
            Thread.sleep(1*1000);
            log.info("[System] ConfigMap data is loading..");
            Thread.sleep(1*1000);
            log.info("[System] ConfigMap data is loading..");
            Thread.sleep(1*1000);
            log.info("[System] Data loading is completed");
            Thread.sleep(1*1000);
            defaultService.isAppReady = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
