package com.sw19.sofa.domain.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw19.sofa.domain.auth.dto.CustomOAuth2User;
import com.sw19.sofa.domain.auth.dto.response.OAuth2Response;
import com.sw19.sofa.security.jwt.provider.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        log.info("OAuth2 로그인 성공");//로그 확인용

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(oAuth2User.getMember().getEncryptUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(oAuth2User.getMember().getEncryptUserId());

        // JSON 응답 생성
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        OAuth2Response oauth2Response = OAuth2Response.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(oauth2Response));
    }
}