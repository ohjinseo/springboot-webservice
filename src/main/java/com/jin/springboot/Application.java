package com.jin.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//2
@SpringBootApplication
//@EnableJpaAuditing  -> WebMvcTest가 JPA 관련 Bean들을 로드하지 않기때문에 오류 발생하여 삭제
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
