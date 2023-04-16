package dev.jlkeesh.shorts.services;

import dev.jlkeesh.shorts.config.security.SessionUser;
import dev.jlkeesh.shorts.dto.UrlCreateDto;
import dev.jlkeesh.shorts.entities.Url;
import dev.jlkeesh.shorts.exceptions.UrlExpiredException;
import dev.jlkeesh.shorts.exceptions.NotFoundException;
import dev.jlkeesh.shorts.mappers.UrlMapper;
import dev.jlkeesh.shorts.repositories.UrlRepository;
import dev.jlkeesh.shorts.utils.BaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public record UrlServiceImpl(
        UrlMapper urlMapper,
        UrlRepository urlRepository,
        BaseUtils utils,
        SessionUser sessionUser) implements UrlService {

    @Override
    public Url create(@NonNull UrlCreateDto dto) {
        log.info("Url Created With : {}, userId : {}", dto, sessionUser.id());
        Url url = urlMapper.toEntity(dto);
        url.setCode(utils.hashUrl(dto.path()));
        return urlRepository.save(url);
    }

    @Override
    public List<Url> getAll() {
        log.info("Urls List Requested userId : {}", sessionUser.id());
        return urlRepository.findByCreatedBy(sessionUser.id());
    }

    @Override
    public Url getByCode(@NotNull String code) {
        Url url = urlRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Url Not Found"));
        if (url.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new UrlExpiredException("Url expired");
        return url;
    }

    @Override
    public List<Url> lastWeek() {
        LocalDateTime monday = LocalDateTime.now().minusWeeks(1).with(DayOfWeek.MONDAY).with(LocalTime.MIN);
        LocalDateTime sunday = LocalDateTime.now().minusWeeks(1).with(DayOfWeek.SUNDAY).with(LocalTime.MAX);
        return urlRepository.weeklyReport(monday,sunday);
    }
}
