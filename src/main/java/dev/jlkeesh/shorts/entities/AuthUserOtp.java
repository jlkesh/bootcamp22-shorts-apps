package dev.jlkeesh.shorts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
public class AuthUserOtp extends Auditable {

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpType otpType;
    @Column(nullable = false)
    private Long userID;

    @Builder(builderMethodName = "childBuilder")
    public AuthUserOtp(Long id, LocalDateTime createdAt,
                       LocalDateTime updateAt, Long createdBy,
                       Long updatedBy, boolean deleted, String code,
                       LocalDateTime expiresAt, OtpType otpType, Long userID) {
        super(id, createdAt, updateAt, createdBy, updatedBy, deleted);
        this.code = code;
        this.expiresAt = expiresAt;
        this.otpType = otpType;
        this.userID = userID;
    }


    public enum OtpType {
        PASSWORD_RESET, ACCOUNT_ACTIVATE
    }
}
