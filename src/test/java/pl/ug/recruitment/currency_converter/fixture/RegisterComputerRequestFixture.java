package pl.ug.recruitment.currency_converter.fixture;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import pl.ug.recruitment.currency_converter.computer.dto.RegisterComputerRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

@With
@NoArgsConstructor
@AllArgsConstructor
public class RegisterComputerRequestFixture {

    private String name = "ACER Aspire";

    private LocalDate bookingDate = LocalDate.of(2026, 7, 13);

    private BigDecimal costUsd = new BigDecimal("345");

    public RegisterComputerRequest build() {
        return new RegisterComputerRequest(
                name,
                bookingDate,
                costUsd
        );
    }
}
