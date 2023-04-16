package dev.jlkeesh.shorts.dto.auth;

public record NewPasswordDTO(
        String code,
        String password,
        String confirmPassword) {
}
