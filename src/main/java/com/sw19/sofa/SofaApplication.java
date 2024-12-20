package com.sw19.sofa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SofaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SofaApplication.class, args);
	}
}