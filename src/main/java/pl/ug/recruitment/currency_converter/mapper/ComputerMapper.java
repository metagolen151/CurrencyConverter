package pl.ug.recruitment.currency_converter.mapper;

import org.mapstruct.Mapper;
import pl.ug.recruitment.currency_converter.computer.Computer;
import pl.ug.recruitment.currency_converter.dto.ComputerResponse;
import pl.ug.recruitment.currency_converter.dto.RegisterComputerRequest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComputerMapper {

    Computer toComputer(RegisterComputerRequest request);

    ComputerResponse toComputerResponse(Computer computer);

    List<ComputerResponse> toListOfComputerResponse(List<Computer> computers);
}
