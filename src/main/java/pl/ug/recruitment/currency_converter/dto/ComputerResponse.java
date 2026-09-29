package pl.ug.recruitment.currency_converter.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ComputerResponse(

        UUID id,

        String name,

        LocalDate bookingDate,

        BigDecimal costUsd,

        BigDecimal costPln
) {
}
