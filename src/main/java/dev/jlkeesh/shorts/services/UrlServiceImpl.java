package dev.jlkeesh.shorts.services;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import dev.jlkeesh.shorts.config.security.SessionUser;
import dev.jlkeesh.shorts.dto.UrlCreateDto;
import dev.jlkeesh.shorts.entities.Url;
import dev.jlkeesh.shorts.exceptions.UrlExpiredException;
import dev.jlkeesh.shorts.exceptions.UrlNotFoundException;
import dev.jlkeesh.shorts.mappers.UrlMapper;
import dev.jlkeesh.shorts.mappers.UrlMapperImpl;
import dev.jlkeesh.shorts.repositories.UrlRepository;
import dev.jlkeesh.shorts.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public record UrlServiceImpl(
        UrlMapper urlMapper,
        UrlRepository urlRepository,
        BaseUtils utils,
        SessionUser sessionUser) implements UrlService {

    @Override
    public Url create(@NonNull UrlCreateDto dto) {
        log.info("Url Created With : {}, userId : {}", dto, sessionUser.getId());
        Url url = urlMapper.toEntity(dto);
        url.setCode(utils.hashUrl(dto.path()));
        return urlRepository.save(url);
    }

    @Override
    public List<Url> getAll() {
        log.info("Urls List Requested userId : {}", sessionUser.getId());
        return urlRepository.findByCreatedBy(sessionUser.getId());
    }

    @Override
    public Url getByCode(@NotNull String code) {
        Url url = urlRepository.findByCode(code, sessionUser().getId())
                .orElseThrow(() -> new UrlNotFoundException("Url Not Found"));
        if (url.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new UrlExpiredException("Url expired");
        return url;
    }
}
