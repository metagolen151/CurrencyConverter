package pl.ug.recruitment.currency_converter.xml;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import pl.ug.recruitment.currency_converter.xml.dto.ComputerXml;
import pl.ug.recruitment.currency_converter.xml.dto.InvoiceXml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class XmlService {

    private final XmlMapper xmlMapper;

    public XmlService() {
        this.xmlMapper = new XmlMapper();
        this.xmlMapper.registerModule(new JavaTimeModule());
        this.xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void generateXml(ComputerXml computer) throws IOException {
        Path path = Path.of("xml","faktura.xml");

        if (Files.notExists(path)) {
            InvoiceXml invoice = new InvoiceXml(List.of(computer));
            String xml = xmlMapper.writeValueAsString(invoice);

            Files.writeString(path, xml);

            return;
        }

        String xml = Files.readString(path);
        InvoiceXml invoice = xmlMapper.readValue(xml, InvoiceXml.class);

        invoice.computers().add(computer);

        String updatedXml = xmlMapper.writeValueAsString(invoice);

        Files.writeString(path, updatedXml);
    }
}
