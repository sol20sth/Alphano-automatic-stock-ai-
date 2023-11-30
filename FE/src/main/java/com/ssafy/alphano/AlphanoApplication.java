package com.ssafy.alphano;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.util.TimeZone;

@SpringBootApplication
@RequiredArgsConstructor
public class AlphanoApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    public void started() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        System.out.println("현재시각: " + java.time.LocalDateTime.now());
    }

    public static void main(String[] args) {
        SpringApplication.run(AlphanoApplication.class);
    }
}
