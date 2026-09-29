package pl.ug.recruitment.currency_converter.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.ug.recruitment.currency_converter.webclient.dto.NbpRateResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class NbpWebClient {

    private final RestTemplate restTemplate;

    //TODO obsłużyć błędy komunikacji z NBP i brak kursu
    public BigDecimal getExchangeRateForDate(LocalDate date) {
        return restTemplate.getForObject(
                "https://api.nbp.pl/api/exchangerates/rates/A/USD/{date}/",
                NbpRateResponse.class, date)
                .rates()
                .getFirst()
                .mid();
    }
}
