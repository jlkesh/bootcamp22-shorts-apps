package dev.jlkeesh.shorts.repositories;

import dev.jlkeesh.shorts.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    @Query("select a from AuthUser a where upper(a.email) = upper(:email) and  a.deleted  = false ")
    Optional<AuthUser> findByEmail(@Param("email") String email);
    @Transactional
    @Modifying
    @Query("update AuthUser a set a.active = true where a.createdBy = :createdBy")
    void activeUser(@Param("createdBy") Long createdBy);
    @Query("select a from AuthUser a where upper(a.username) = upper(?1) and  a.deleted = false ")
    Optional<AuthUser> findByUsername(String username);
}