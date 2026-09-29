package pl.ug.recruitment.currency_converter.exception.dto;

import java.time.LocalDateTime;

public record ExceptionResponse(
        String message,
        LocalDateTime timestamp,
        int status
) {
}
