package com.sw19.sofa.security.config;

import com.sw19.sofa.security.jwt.error.CustomAuthenticationEntryPoint;
import com.sw19.sofa.security.jwt.error.JwtExceptionFilter;
import com.sw19.sofa.security.jwt.filter.JwtAuthenticationFilter;
import com.sw19.sofa.security.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] PERMIT_URLS = {
            /* swagger */
            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources", "/swagger-resources/**",

            /* health check */
            "/health-check",

            /* auth */
            "/auth/**",
    };

    private static final String[] SEMI_PERMIT_URLS = {
      //GET만 허용해야 하는 URL
    };

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headerConfig -> headerConfig.frameOptions(FrameOptionsConfig::disable))
        ;

        http.authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(PERMIT_URLS).permitAll()
                                .requestMatchers(HttpMethod.GET, SEMI_PERMIT_URLS).permitAll()
                                .anyRequest().authenticated()

                        )
                .exceptionHandling(exceptionConfig ->
                        exceptionConfig.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}