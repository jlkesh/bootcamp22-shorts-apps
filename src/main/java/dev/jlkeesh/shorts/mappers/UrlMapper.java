package dev.jlkeesh.shorts.mappers;

import dev.jlkeesh.shorts.dto.UrlCreateDto;
import dev.jlkeesh.shorts.entities.Url;
import org.mapstruct.Mapper;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring")
public interface UrlMapper {
    Url toEntity(@NonNull UrlCreateDto dto);
}
