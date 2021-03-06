package com.dsmupgrade.global.security;

import com.dsmupgrade.global.error.exception.ExpiredTokenException;
import com.dsmupgrade.global.error.exception.InvalidTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${auth.jwt.secret}")
    private String secret;

    @Value("${auth.jwt.exp.access}")
    private Long accessExpiration;

    @Value("${auth.jwt.exp.refresh}")
    private Long refreshExpiration;

    @Value("${auth.jwt.header}")
    private String header;

    @Value("${auth.jwt.prefix}")
    private String prefix;

    private final AuthDetailsService authDetailsService;

    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration * 1000))
                .setSubject(username)
                .claim("type", "access_token")
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration * 1000))
                .setSubject(username)
                .claim("type", "refresh_token")
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith(prefix)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public void validateToken(String token) {
        try {
            String type = Jwts.parser().setSigningKey(secret.getBytes())
                    .parseClaimsJws(token).getBody().get("type", String.class);
            assert type.equals("access_token");
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public void validateRefreshToken(String token) {
        try {
            String type = Jwts.parser().setSigningKey(secret.getBytes())
                    .parseClaimsJws(token).getBody().get("type", String.class);
            assert type.equals("refresh_token");
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public Authentication getAuthentication(String token) {
        AuthDetails authDetails = (AuthDetails) authDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(authDetails, "", authDetails.getAuthorities());
    }

    public String getUsername(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes())
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }
}
