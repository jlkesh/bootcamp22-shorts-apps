package dev.jlkeesh.shorts.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class WeeklyReportDTO {
    private Integer totalCount;
    private String from;
    private String to;
    private List<DailyReportDTO> dailyReportDTOList;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public WeeklyReportDTO(List<DailyReportDTO> dtos, LocalDateTime from, LocalDateTime to, Integer totalCount) {
        this.totalCount = totalCount;
        this.from = DATE_TIME_FORMATTER.format(from);
        this.to = DATE_TIME_FORMATTER.format(to);
        this.dailyReportDTOList = dtos;
    }
}
