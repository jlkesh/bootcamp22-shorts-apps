package dev.jlkeesh.shorts.services;

import dev.jlkeesh.shorts.dto.auth.*;
import dev.jlkeesh.shorts.entities.AuthUser;
import org.springframework.lang.NonNull;

public interface AuthService {

    TokenResponse generateToken(@NonNull TokenRequest tokenRequest);

    AuthUser create(@NonNull AuthUserCreateDTO dto);

    boolean activateUserByOTP(String code);

    TokenResponse refreshToken(@NonNull RefreshTokenRequest refreshTokenRequest);

    String sendActionCode(@NonNull ActivationCodeResendDTO dto);

}
