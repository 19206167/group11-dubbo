package com.example.group11;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FileName: MainApplication.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 18:58
 */
@SpringBootApplication
@Slf4j
@EnableDubbo
public class FileProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileProviderApplication.class, args);
    }
}
