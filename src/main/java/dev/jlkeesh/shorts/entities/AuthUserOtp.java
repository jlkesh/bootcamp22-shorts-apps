package dev.jlkeesh.shorts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AuthUserOtp extends Auditable {

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Builder(builderMethodName = "childBuilder")
    public AuthUserOtp(Long id, LocalDateTime createdAt, LocalDateTime updateAt, Long createdBy, Long updatedBy, boolean deleted, String code, LocalDateTime expiresAt) {
        super(id, createdAt, updateAt, createdBy, updatedBy, deleted);
        this.code = code;
        this.expiresAt = expiresAt;
    }
}
