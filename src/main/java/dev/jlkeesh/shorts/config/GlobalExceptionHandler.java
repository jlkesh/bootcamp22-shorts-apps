package dev.jlkeesh.shorts.config;

import dev.jlkeesh.shorts.dto.response.AppErrorDTO;
import dev.jlkeesh.shorts.exceptions.UrlExpiredException;
import dev.jlkeesh.shorts.exceptions.UrlNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String errorMessage = "Input is not valid";
        Map<String, List<String>> errorBody = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorBody.compute(field, (s, values) -> {
                if (!Objects.isNull(values))
                    values.add(message);
                else
                    values = new ArrayList<>(Collections.singleton(message));
                return values;
            });
        }
        String errorPath = request.getRequestURI();
        AppErrorDTO errorDTO = new AppErrorDTO(errorPath, errorMessage, errorBody, 400);
        return ResponseEntity.status(400).body(errorDTO);
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<AppErrorDTO> handleUrlNotFoundException(UrlNotFoundException e, HttpServletRequest request) {
        String errorMessage = e.getMessage();
        String errorPath = request.getRequestURI();
        AppErrorDTO errorDTO = new AppErrorDTO(errorPath, errorMessage, 404);
        return ResponseEntity.status(404).body(errorDTO);
    }

    @ExceptionHandler(UrlExpiredException.class)
    public ResponseEntity<AppErrorDTO> handleUrlExpiredException(UrlExpiredException e, HttpServletRequest request) {
        String errorMessage = e.getMessage();
        String errorPath = request.getRequestURI();
        AppErrorDTO errorDTO = new AppErrorDTO(errorPath, errorMessage, 400);
        return ResponseEntity.status(400).body(errorDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AppErrorDTO> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String errorMessage = "Internal Server Error";
        String errorBody = e.getMessage();
        String errorPath = request.getRequestURI();
        AppErrorDTO errorDTO = new AppErrorDTO(errorPath, errorMessage, errorBody, 500);
        return ResponseEntity.status(500).body(errorDTO);
    }
}
