package dev.jlkeesh.shorts.services;

import dev.jlkeesh.shorts.dto.auth.AuthUserCreateDTO;
import dev.jlkeesh.shorts.dto.auth.RefreshTokenRequest;
import dev.jlkeesh.shorts.dto.auth.TokenRequest;
import dev.jlkeesh.shorts.dto.auth.TokenResponse;
import dev.jlkeesh.shorts.entities.AuthUser;
import org.springframework.lang.NonNull;

public interface AuthService {

    TokenResponse generateToken(@NonNull TokenRequest tokenRequest);

    AuthUser create(@NonNull AuthUserCreateDTO dto);

    boolean activateUserByOTP(String code);

    TokenResponse refreshToken(@NonNull RefreshTokenRequest refreshTokenRequest);
}
