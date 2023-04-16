package dev.jlkeesh.shorts.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String accessToken;
    private Date accessTokenExpiry;
    private String refreshToken;
    private Date refreshTokenExpiry;

    public TokenResponse(long accessTokenExpiry, long refreshTokenExpiry) {
        this.accessTokenExpiry = new Date(System.currentTimeMillis() + accessTokenExpiry);
        this.refreshTokenExpiry = new Date(System.currentTimeMillis() + refreshTokenExpiry);
    }
}
