package pl.ug.recruitment.currency_converter.xml.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ComputerXml(

        @JacksonXmlProperty(localName = "nazwa")
        String name,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @JacksonXmlProperty(localName = "data_ksiegowania")
        LocalDate bookingDate,

        @JacksonXmlProperty(localName = "koszt_USD")
        BigDecimal costUsd,

        @JacksonXmlProperty(localName = "koszt_PLN")
        BigDecimal costPln
) {
}
