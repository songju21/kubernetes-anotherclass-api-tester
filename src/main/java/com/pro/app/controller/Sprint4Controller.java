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



    public Sprint4Controller() {
    }

    @GetMapping("/user_info")
    @ResponseBody
    public ResponseEntity<Object> userInfo()  {

        String returnString = "{"
                + "\"name\": \"일프로\","
                + "\"ssn\": \"831006-1234567\","
                + "\"userId\": \"1pro\","
                + "\"email\": \"k8s.1pro@gmail.com\","
                + "\"phone\": \"010-1234-5678\","
                + "\"role\": \"admin\","
                + "\"department\": \"IT\","
                + "\"position\": \"DevOps Engineer\","
                + "\"address\": {"
                + "\"city\": \"Incheon\","
                + "\"district\": \"Namdonggu\","
                + "},"
                + "\"loginHistory\": ["
                + "{ \"timestamp\": \"2024-02-25T10:15:30\", \"ip\": \"192.168.0.1\", \"device\": \"Windows PC\" },"
                + "{ \"timestamp\": \"2024-02-24T22:30:45\", \"ip\": \"192.168.0.5\", \"device\": \"iPhone 13\" }"
                + "],"
                + "\"permissions\": [\"READ\", \"WRITE\", \"DELETE\"]"
                + "}";
        log.info(returnString);
        return ResponseEntity.ok(returnString);
    }



    @GetMapping("/first_log/to/{serviceName}")
    @ResponseBody
    public ResponseEntity<Object> firstLog(@PathVariable String serviceName,
            @RequestHeader(value = "X-User-Id", required = false) String userId)  {

        sprint4Service.firstRequest(userId, serviceName);
        return ResponseEntity.ok("ok");
    }


    @GetMapping("/second_log/{count}")
    @ResponseBody
    public ResponseEntity<Object> secondLog(@PathVariable Integer count) {
        sprint4Service.secondRequest(count);
        return ResponseEntity.ok("ok");
    }

}