package com.example.group11;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * FileName: QAConsumerApplication.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 19:17
 */
@EnableCaching
@SpringBootApplication
@Slf4j
@EnableDubbo
public class QAConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(QAConsumerApplication.class, args);
    }
}
