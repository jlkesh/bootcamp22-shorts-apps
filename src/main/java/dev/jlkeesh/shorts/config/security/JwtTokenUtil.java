package dev.jlkeesh.shorts.config.security;

import dev.jlkeesh.shorts.dto.auth.TokenResponse;
import dev.jlkeesh.shorts.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static dev.jlkeesh.shorts.enums.TokenType.ACCESS;
import static dev.jlkeesh.shorts.enums.TokenType.REFRESH;

@Component
public class JwtTokenUtil {


    @Value("#{T(java.lang.System).currentTimeMillis()  + ${jwt.access.token.expiry}}")
    private long accessTokenExpiry;

    @Value("${jwt.access.token.secret.key}")
    public String ACCESS_TOKEN_SECRET_KEY;


    @Value("#{T(java.lang.System).currentTimeMillis()  + ${jwt.refresh.token.expiry}}")
    private long refreshTokenExpiry;

    @Value("${jwt.refresh.token.secret.key}")
    public String REFRESH_TOKEN_SECRET_KEY;


    public TokenResponse generateToken(@NonNull String username) {
        TokenResponse tokenResponse = new TokenResponse(accessTokenExpiry, refreshTokenExpiry);
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("https://online.pdp.uz")
                .setExpiration(tokenResponse.getAccessTokenExpiry())
                .signWith(signKey(ACCESS), SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("https://online.pdp.uz")
                .setExpiration(tokenResponse.getAccessTokenExpiry())
                .signWith(signKey(REFRESH), SignatureAlgorithm.HS256)
                .compact();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return tokenResponse;
    }

    public boolean isValid(@NonNull String token, TokenType tokenType) {
        try {
            Claims claims = getClaims(token, tokenType);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsername(@NonNull String token, TokenType tokenType) {
        Claims claims = getClaims(token, tokenType);
        return claims.getSubject();
    }

    private Claims getClaims(String token, TokenType tokenType) {
        return Jwts.parserBuilder()
                .setSigningKey(signKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key signKey(TokenType tokenType) {
        byte[] bytes = Decoders.BASE64.decode(tokenType.equals(ACCESS) ? ACCESS_TOKEN_SECRET_KEY : REFRESH_TOKEN_SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }
}
