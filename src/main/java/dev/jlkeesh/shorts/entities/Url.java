package dev.jlkeesh.shorts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Url extends Auditable {
    @Column(name = "path")
    private String path;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "description", length = 400)
    private String description;


    @Builder(builderMethodName = "childBuilder")
    public Url(Long id, LocalDateTime createdAt, LocalDateTime updateAt, Long createdBy, Long updatedBy, boolean deleted, String path, String code, LocalDateTime expiresAt, String description) {
        super(id, createdAt, updateAt, createdBy, updatedBy, deleted);
        this.path = path;
        this.code = code;
        this.expiresAt = expiresAt;
        this.description = description;
    }

    @PrePersist
    public void prePersist() {
        if (Objects.isNull(this.expiresAt)) {
            expiresAt = LocalDateTime.now().plusDays(10);
        }
    }
}
