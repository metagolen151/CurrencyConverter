package pl.ug.recruitment.currency_converter.webclient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import pl.ug.recruitment.currency_converter.webclient.dto.NbpRate;
import pl.ug.recruitment.currency_converter.webclient.dto.NbpRateResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NbpWebClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NbpWebClient nbpWebClient;

    @Test
    void shouldReturnExchangeRate() {
        //given
        LocalDate date = LocalDate.of(2026, 7, 13);
        BigDecimal rate = new BigDecimal("3.654");

        NbpRate nbpRate = new NbpRate(rate);
        NbpRateResponse response = new NbpRateResponse(List.of(nbpRate));

        when(restTemplate.getForObject(
                "https://api.nbp.pl/api/exchangerates/rates/A/USD/{date}/",
                NbpRateResponse.class,
                date
        )).thenReturn(response);
        //when
        BigDecimal result = nbpWebClient.getExchangeRateForDate(date);
        //then
        assertEquals(rate, result);
    }
}