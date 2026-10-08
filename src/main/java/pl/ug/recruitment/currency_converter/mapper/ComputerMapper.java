package pl.ug.recruitment.currency_converter.mapper;

import org.mapstruct.Mapper;
import pl.ug.recruitment.currency_converter.computer.Computer;
import pl.ug.recruitment.currency_converter.computer.dto.ComputerResponse;
import pl.ug.recruitment.currency_converter.computer.dto.RegisterComputerRequest;
import pl.ug.recruitment.currency_converter.xml.dto.ComputerXml;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComputerMapper {

    Computer toComputer(RegisterComputerRequest request);

    ComputerResponse toComputerResponse(Computer computer);

    List<ComputerResponse> toListOfComputerResponse(List<Computer> computers);

    ComputerXml toComputerXml(ComputerResponse response);
}
