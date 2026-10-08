package pl.ug.recruitment.currency_converter.computer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterComputerRequest(

        @NotBlank(message = "Computer name is required")
        @Size(max = 100, message = "Computer name cannot exceed 100 characters")
        String name,

        @NotNull(message = "Booking date is required")
        LocalDate bookingDate,

        @NotNull(message = "USD cost is required")
        @Positive(message = "USD cost must be greater than zero")
        BigDecimal costUsd
) {
}
