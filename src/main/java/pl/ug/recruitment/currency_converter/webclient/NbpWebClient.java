package pl.ug.recruitment.currency_converter.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.ug.recruitment.currency_converter.webclient.dto.NbpRateResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class NbpWebClient {
    private final RestTemplate restTemplate;

    private final String nbpApiUrl;

    public NbpWebClient(
            RestTemplate restTemplate,
            @Value("${nbpApiUrl}") String nbpApiUrl
    ) {
        this.restTemplate = restTemplate;
        this.nbpApiUrl = nbpApiUrl;
    }
    //TODO obsłużyć błędy komunikacji z NBP i brak kursu

    public BigDecimal getExchangeRateForDate(LocalDate date) {
        return restTemplate.getForObject(
                nbpApiUrl, NbpRateResponse.class,
                date)
                .rates()
                .getFirst()
                .mid();
    }
}
