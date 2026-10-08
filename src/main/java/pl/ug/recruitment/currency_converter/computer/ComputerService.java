package pl.ug.recruitment.currency_converter.computer;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ug.recruitment.currency_converter.computer.dto.ComputerResponse;
import pl.ug.recruitment.currency_converter.computer.dto.RegisterComputerRequest;
import pl.ug.recruitment.currency_converter.exception.XmlGenerationException;
import pl.ug.recruitment.currency_converter.mapper.ComputerMapper;
import pl.ug.recruitment.currency_converter.webclient.NbpWebClient;
import pl.ug.recruitment.currency_converter.xml.XmlService;

import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ComputerService {

    private final ComputerRepository repository;

    private final NbpWebClient nbpWebClient;

    private final WorkingDayService workingDayService;

    private final XmlService xmlService;

    private final ComputerMapper computerMapper;

    @Transactional
    public ComputerResponse registerComputer(RegisterComputerRequest request) {
        LocalDate date = request.bookingDate();

        //TODO dodać obsługę dni świątecznych
        while(!workingDayService.isWorkingDay(date)) {
            date = date.minusDays(1);
        }

        Computer computer = computerMapper.toComputer(request);

        BigDecimal rate = nbpWebClient.getExchangeRateForDate(date);

        BigDecimal costPln = request.costUsd()
                .multiply(rate)
                .setScale(2, RoundingMode.HALF_UP);

        computer.setCostPln(costPln);

        Computer savedComputer = repository.save(computer);

        ComputerResponse response = computerMapper.toComputerResponse(savedComputer);

        try {
            xmlService.generateXml(computerMapper.toComputerXml(response));
        } catch (IOException e) {
            throw new XmlGenerationException(e);
        }

        return response;
    }

    public Page<ComputerResponse> getAllComputers(
            String name,
            LocalDate bookingDate,
            Pageable pageable
    ) {
        Page<Computer> computers = repository.search(
                name,
                bookingDate,
                pageable
        );

        return computers.map(computerMapper::toComputerResponse);
    }
}
