package com.sw19.sofa.domain.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth2.google")
public class GoogleOAuth2Config {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String tokenUri;
    private String resourceUri;
}