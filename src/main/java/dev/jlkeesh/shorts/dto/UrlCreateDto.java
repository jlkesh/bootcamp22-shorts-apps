package dev.jlkeesh.shorts.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link dev.jlkeesh.shorts.entities.Url} entity
 */


public record UrlCreateDto(
        @NotBlank String path,
         @Future LocalDateTime expiresAt,
        String description) implements Serializable {
}