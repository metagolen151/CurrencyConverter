package pl.ug.recruitment.currency_converter.xml.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "faktura")
public record InvoiceXml(

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "komputer")
        List<ComputerXml> computers
) {
}
