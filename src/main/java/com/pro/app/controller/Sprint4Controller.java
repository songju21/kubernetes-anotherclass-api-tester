package com.pro.app.controller;

import com.pro.app.component.FileUtils;
import com.pro.app.service.Sprint4Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Sprint4Controller {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private Sprint4Service sprint4Service;

    @Autowired
    private FileUtils fileUtils;

    @Value(value = "${application.code}")
    private String applicationCode;

    public Sprint4Controller() {
    }

    @GetMapping("/first_log/to/{serviceName}")
    @ResponseBody
    public ResponseEntity<Object> firstLog(@PathVariable String serviceName,
            @RequestHeader(value = "X-User-Id", required = false, defaultValue = "anonymous") String userId)  {

        sprint4Service.firstRequest(applicationCode, serviceName, userId);
        return ResponseEntity.ok("ok");
    }


    @GetMapping("/second_log")
    @ResponseBody
    public ResponseEntity<Object> secondLog(@RequestHeader(value = "X-User-Id", required = false, defaultValue = "anonymous") String userId,
                                            @RequestHeader(value = "X-Trace-Id") String traceId) {

        sprint4Service.secondRequest(applicationCode, userId, traceId);
        return ResponseEntity.ok("ok");
    }

}