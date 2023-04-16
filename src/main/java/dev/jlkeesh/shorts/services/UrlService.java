package dev.jlkeesh.shorts.services;

import dev.jlkeesh.shorts.dto.UrlCreateDto;
import dev.jlkeesh.shorts.entities.Url;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UrlService {
    Url create(@NonNull UrlCreateDto dto);

    List<Url> getAll();

    Url getByCode(@NonNull String code);

    List<Url>lastWeek();
}
