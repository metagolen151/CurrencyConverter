package pl.ug.recruitment.currency_converter.fixture;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import pl.ug.recruitment.currency_converter.dto.ComputerResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@With
@NoArgsConstructor
@AllArgsConstructor
public class ComputerResponseFixture {

    private UUID id = UUID.randomUUID();

    private String name = "ACER Aspire";

    private LocalDate bookingDate = LocalDate.of(2026, 7, 13);

    private BigDecimal costUsd = new BigDecimal("345");

    private BigDecimal costPln = new BigDecimal("1260.63");

    public ComputerResponse build() {
        return new ComputerResponse(
                id,
                name,
                bookingDate,
                costUsd,
                costPln
        );
    }
}
