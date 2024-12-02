package com.sw19.sofa.domain.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw19.sofa.domain.auth.dto.CustomOAuth2User;
import com.sw19.sofa.domain.auth.dto.response.OAuth2Response;
import com.sw19.sofa.security.jwt.provider.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.frontend.redirect.url}")
    private String frontendRedirectUrl;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        try {
            String accessToken = jwtTokenProvider.createAccessToken(oAuth2User.getMember().getEncryptUserId());
            String refreshToken = jwtTokenProvider.createRefreshToken(oAuth2User.getMember().getEncryptUserId());

            String targetUrl = UriComponentsBuilder.fromUriString(frontendRedirectUrl)
                    .queryParam("accessToken", accessToken)
                    .queryParam("refreshToken", refreshToken)
                    .build().toUriString();

            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } catch (Exception e) {
            throw new IOException("Failed to process authentication success", e);
        }
    }
}
