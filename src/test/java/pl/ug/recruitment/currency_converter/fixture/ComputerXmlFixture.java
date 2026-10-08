package pl.ug.recruitment.currency_converter.fixture;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import pl.ug.recruitment.currency_converter.xml.dto.ComputerXml;

import java.math.BigDecimal;
import java.time.LocalDate;

@With
@NoArgsConstructor
@AllArgsConstructor
public class ComputerXmlFixture {

    private String name = "ACER Aspire";

    private LocalDate bookingDate = LocalDate.of(2026, 7, 13);

    private BigDecimal costUsd = new BigDecimal("345");

    private BigDecimal costPln = new BigDecimal("1305.69");

    public ComputerXml build() {
        return new ComputerXml(
                name,
                bookingDate,
                costUsd,
                costPln );
    }
}
