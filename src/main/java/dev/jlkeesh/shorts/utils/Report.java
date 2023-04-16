package dev.jlkeesh.shorts.utils;

import dev.jlkeesh.shorts.config.security.SessionUser;
import dev.jlkeesh.shorts.config.security.UserDetails;
import dev.jlkeesh.shorts.dto.UrlResultDTO;
import dev.jlkeesh.shorts.dto.report.DailyReportDTO;
import dev.jlkeesh.shorts.dto.report.UrlReport;
import dev.jlkeesh.shorts.dto.report.WeeklyReportDTO;
import dev.jlkeesh.shorts.entities.Auditable;
import dev.jlkeesh.shorts.entities.Url;
import dev.jlkeesh.shorts.services.UrlService;
import dev.jlkeesh.shorts.services.UrlServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Report {
    private final UrlService urlService;
    private final SessionUser sessionUser;
    private final MailSenderService mailSenderService;

    public WeeklyReportDTO report() {
        UserDetails user = sessionUser.user();
        AtomicInteger counter = new AtomicInteger(0);
        List<DailyReportDTO> dailyReportDTOS = new ArrayList<>();

        urlService.lastWeek()
                .stream().map(url -> {
                    counter.incrementAndGet();
                    return new UrlReport(url);
                })
                .collect(Collectors.groupingBy(urlReport -> urlReport.getDayOfWeek().getValue()))
                .forEach((k, v) -> {
                    DailyReportDTO dailyReportDTO = new DailyReportDTO(v, DayOfWeek.of(k));
                    dailyReportDTOS.add(dailyReportDTO);
                });
        WeeklyReportDTO weeklyReportDTO = new WeeklyReportDTO(dailyReportDTOS, LocalDateTime.now(), LocalDateTime.now(), counter.get());

        Map<String, Object> model = Map.of(
                "to", user.getEmail(),
                "subject", "Weekly Report",
                "weeklyReport", weeklyReportDTO);
        mailSenderService.report(model);
        return weeklyReportDTO;
    }
}
