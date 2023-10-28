package com.example.group11;

import lombok.extern.slf4j.Slf4j;
//import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FileName: UserProviderApplication.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 19:21
 */
@SpringBootApplication
@Slf4j
@EnableDubbo
public class UserProviderApplication {
    public static void main(String[] args) {
        //update using
        SpringApplication.run(UserProviderApplication.class, args);
    }
}
