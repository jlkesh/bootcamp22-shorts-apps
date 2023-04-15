package dev.jlkeesh.shorts.repositories;

import dev.jlkeesh.shorts.entities.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    @Query("select u from Url u where u.code = ?1 and u.deleted = false")
    Optional<Url> findByCode(String code);
    @Query("select u from Url u where u.createdBy = ?1")
    List<Url> findByCreatedBy(Long createdBy);
}