package com.sw19.sofa.security.jwt.provider;

import com.sw19.sofa.infra.redis.service.RedisService;
import com.sw19.sofa.security.jwt.error.CustomJwtException;
import com.sw19.sofa.security.jwt.error.code.JwtErrorCode;
import com.sw19.sofa.security.service.CustomMemberService;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

import static com.sw19.sofa.infra.redis.constants.RedisPrefix.REFRESH_TOKEN;
import static com.sw19.sofa.security.jwt.constants.JwtConstants.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-token-valid-minute}")
    private long accessTokenValidTime;
    @Value("${jwt.refresh-token-valid-month}")
    private long refreshTokenValidTime;
    private SecretKeySpec secretKeySpec;

    private final CustomMemberService memberDetailsService;
    private final RedisService redisService;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes());
        this.secretKeySpec = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        accessTokenValidTime = accessTokenValidTime * 60 * 1000L;
        refreshTokenValidTime = refreshTokenValidTime * 30 * 24 * 60 * 60 * 1000L;
    }

    public String createAccessToken(String encryptId) {
        return createJwtToken(encryptId, accessTokenValidTime, ACCESS_TOKEN_TYPE.getValue());
    }

    public String createRefreshToken(String encryptId) {
        String token = createJwtToken(encryptId, refreshTokenValidTime, REFRESH_TOKEN_TYPE.getValue());

        redisService.save(REFRESH_TOKEN, encryptId, token, REFRESH_TOKEN.getDuration());
        return token;

    }

    private String createJwtToken(String encryptId, Long validityTime, String tokenType) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityTime);

        return Jwts.builder()
                .setSubject(encryptId)
                .claim(TOKEN_TYPE.getValue(), tokenType)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKeySpec, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKeySpec)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Authentication getAuthentication(String token) {
        String userId = getBody(token)
                .getSubject();
        UserDetails principal = memberDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Date getExpiration(String token) {
        return getBody(token).getExpiration();
    }

    public String getUserIdFromToken(String token) {
        return getBody(token).getSubject();
    }

    public void validateRefreshToken(String refreshToken, String userId) {
        validateToken(refreshToken);
        String tokenType = getBody(refreshToken).get(TOKEN_TYPE.getValue(), String.class);

        if(!tokenType.equals(REFRESH_TOKEN_TYPE.getValue())){
            throw new CustomJwtException(JwtErrorCode.INVALID_REFRESH);
        }

        if (redisService.hasNoKey(REFRESH_TOKEN, userId)) {
            throw new CustomJwtException(JwtErrorCode.CANNOT_REFRESH);
        }

        String redisToken = (String) redisService.get(REFRESH_TOKEN, userId);

        if (!redisToken.equals(refreshToken)) {
            throw new CustomJwtException(JwtErrorCode.INVALID_JWT_TOKEN);
        }
    }

    public boolean validateToken(String token) {
        if (token == null) {
            throw new CustomJwtException(JwtErrorCode.EMPTY_TOKEN);
        }

        try {
            Claims claimsBody = getBody(token);

            return !claimsBody.getExpiration().before(new Date()) && claimsBody.get(TOKEN_TYPE.getValue(), String.class).equals(ACCESS_TOKEN_TYPE.getValue());
        } catch (SecurityException | MalformedJwtException e) {
            log.info("JWT 토큰이 유효하지 않습니다.");
            throw new CustomJwtException(JwtErrorCode.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("JWT 토큰이 만료되었습니다.");
            throw new CustomJwtException(JwtErrorCode.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다.");
            throw new CustomJwtException(JwtErrorCode.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
