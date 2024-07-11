package dev.martin1500.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, Object> errors = new LinkedHashMap<>();

        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        errors.put("message", "Validation failed");
        errors.put("code", "ERR_1001");

        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        errors.put("errors", fieldErrors);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
