package kz.kbtu.banurr.webhw2.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class ErrorData {

    private String code;
    private String message;
    private OffsetDateTime timestamp;
}
