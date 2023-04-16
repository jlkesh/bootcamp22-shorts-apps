package dev.jlkeesh.shorts.controllers;

import dev.jlkeesh.shorts.dto.UrlCreateDto;
import dev.jlkeesh.shorts.dto.UrlResultDTO;
import dev.jlkeesh.shorts.dto.report.WeeklyReportDTO;
import dev.jlkeesh.shorts.entities.Url;
import dev.jlkeesh.shorts.services.UrlService;
import dev.jlkeesh.shorts.utils.Report;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private final Report report;

    @PostMapping("/api/url")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Url> create(@NonNull @Valid @RequestBody UrlCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(urlService.create(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/url")
    public ResponseEntity<List<Url>> getAll() {
        return ResponseEntity.ok(urlService.getAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Url> getUrlByTokenAndRedirect(@PathVariable String code) {
        return ResponseEntity.ok(urlService.getByCode(code));
    }

    @GetMapping("/report")
    public ResponseEntity<WeeklyReportDTO> report() {
        return ResponseEntity.ok(this.report.report());
    }
}
