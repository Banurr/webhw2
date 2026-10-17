package kz.kbtu.banurr.webhw2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        response.put("errors", errors);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(KaseException.class)
    public ResponseEntity<ErrorData> handleKaseException(KaseException ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorData("KASE_ERROR", ex.getMessage(), OffsetDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorData> handleException(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorData("INTERNAL_SERVER_ERROR", ex.getMessage(), OffsetDateTime.now()));
    }

}
