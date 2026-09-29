package pl.ug.recruitment.currency_converter.webclient.dto;

import java.util.List;

public record NbpRateResponse(
        List<NbpRate> rates
) {
}
