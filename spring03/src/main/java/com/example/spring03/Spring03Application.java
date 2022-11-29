package com.example.spring03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // JPA의 AuditingEntityListener 기능을 활성화하기 위해서.
public class Spring03Application {

	public static void main(String[] args) { // main 메서드가 있기 때문에 jdk만 있음 어디서든 실행가능한 jar 파일이 됨
	    
		SpringApplication.run(Spring03Application.class, args);
	}

}
