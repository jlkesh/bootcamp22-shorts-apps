package dev.jlkeesh.shorts.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportDTO {

    private String dayName;
    private Integer dayNumber;
    private Integer totalCount;
    private List<UrlReport> urlReports;

    public DailyReportDTO(List<UrlReport> dtos, DayOfWeek dayOfWeek) {
        this.dayName = dayOfWeek.name();
        this.dayNumber = dayOfWeek.getValue();
        this.totalCount = dtos.size();
        this.urlReports = dtos;
    }
}
