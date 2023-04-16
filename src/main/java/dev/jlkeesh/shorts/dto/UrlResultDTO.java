package dev.jlkeesh.shorts.dto;

import dev.jlkeesh.shorts.entities.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlResultDTO {

    private LocalDate createdAt;
    private DayOfWeek dayOfWeek;
    private String code;
    private String description;
    private String path;

    public UrlResultDTO(Url url) {
        this.code = url.getCode();
        this.createdAt = url.getCreatedAt().toLocalDate();
        this.description = url.getDescription();
        this.path = url.getPath();
        this.dayOfWeek = createdAt.getDayOfWeek();
    }
}
