package pl.ug.recruitment.currency_converter.xml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.ug.recruitment.currency_converter.fixture.ComputerXmlFixture;
import pl.ug.recruitment.currency_converter.xml.dto.ComputerXml;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class XmlServiceTest {

    private XmlService xmlService = new XmlService();

    @AfterEach
    void cleanUp() throws IOException {
        Path path = Path.of("xml", "faktura.xml");

        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    @Test
    void shouldCreateXmlFile() throws IOException {
        // given
        ComputerXml computer = new ComputerXmlFixture().build();

        // when
        xmlService.generateXml(computer);

        // then
        Path path = Path.of("xml", "faktura.xml");

        assertTrue(Files.exists(path));

        String xml = Files.readString(path);

        assertTrue(xml.contains("<nazwa>ACER Aspire</nazwa>"));
        assertTrue(xml.contains("<data_ksiegowania>2026-07-13</data_ksiegowania>"));
        assertTrue(xml.contains("<koszt_USD>345</koszt_USD>"));
        assertTrue(xml.contains("<koszt_PLN>1305.69</koszt_PLN>"));
    }

    @Test
    void shouldAppendComputerToExistingXml() throws IOException {
        // given
        ComputerXml computer1 = new ComputerXmlFixture().build();

        ComputerXml computer2 = new ComputerXmlFixture()
                .withName("DELL Latitude")
                .withBookingDate(LocalDate.of(2026, 7, 15))
                .withCostUsd(new BigDecimal("543"))
                .withCostPln(new BigDecimal("2064.76"))
                .build();

        xmlService.generateXml(computer1);

        // when
        xmlService.generateXml(computer2);

        // then
        Path path = Path.of("xml", "faktura.xml");
        String xml = Files.readString(path);

        assertTrue(xml.contains("<nazwa>ACER Aspire</nazwa>"));
        assertTrue(xml.contains("<nazwa>DELL Latitude</nazwa>"));
    }
}