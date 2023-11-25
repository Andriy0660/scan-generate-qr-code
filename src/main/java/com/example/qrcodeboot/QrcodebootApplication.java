package com.example.qrcodeboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class QrcodebootApplication {

    public static void main(String[] args){
        SpringApplication.run(QrcodebootApplication.class, args);
    }
}
