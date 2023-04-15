package dev.jlkeesh.shorts.repositories;

import dev.jlkeesh.shorts.entities.AuthUserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AuthUserOtpRepository extends JpaRepository<AuthUserOtp, Long> {
    @Query("select a from AuthUserOtp a where a.code = ?1 and a.deleted = false")
    Optional<AuthUserOtp> findByCode(String code);
}