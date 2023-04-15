package dev.jlkeesh.shorts.controllers;

import dev.jlkeesh.shorts.dto.UrlCreateDto;
import dev.jlkeesh.shorts.entities.Url;
import dev.jlkeesh.shorts.services.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/api/url")
    public ResponseEntity<Url> create(@NonNull @Valid @RequestBody UrlCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(urlService.create(dto));
    }

    @GetMapping("/api/url")
    public ResponseEntity<List<Url>> getAll() {
        return ResponseEntity.ok(urlService.getAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> getUrlByTokenAndRedirect(@PathVariable String code, HttpServletResponse res) throws IOException {
        Url url = urlService.getByCode(code);
        res.sendRedirect(url.getPath());
        return ResponseEntity.notFound().build();
    }
}