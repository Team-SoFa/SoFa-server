package com.sw19.sofa;

import com.sw19.sofa.domain.auth.config.GoogleOAuth2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(GoogleOAuth2Config.class)
@EnableJpaAuditing
public class SofaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SofaApplication.class, args);
	}
}