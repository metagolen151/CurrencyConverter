package pl.ug.recruitment.currency_converter.webclient.dto;

import java.math.BigDecimal;

public record NbpRate(
        BigDecimal mid
) {
}
