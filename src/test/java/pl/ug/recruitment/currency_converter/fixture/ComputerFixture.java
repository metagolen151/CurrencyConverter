package pl.ug.recruitment.currency_converter.fixture;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import pl.ug.recruitment.currency_converter.computer.Computer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@With
@NoArgsConstructor
@AllArgsConstructor
public class ComputerFixture {

    private UUID id = UUID.randomUUID();

    private String name = "ACER Aspire";

    private LocalDate bookingDate = LocalDate.of(2026, 7, 3);

    private BigDecimal costUsd = new BigDecimal("345");

    private BigDecimal costPln = new BigDecimal("1290.99");

    public Computer build() {
        return new Computer(
                id,
                name,
                bookingDate,
                costUsd,
                costPln
        );
    }
}