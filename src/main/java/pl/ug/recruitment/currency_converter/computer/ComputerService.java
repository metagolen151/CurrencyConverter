package pl.ug.recruitment.currency_converter.computer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ug.recruitment.currency_converter.dto.ComputerResponse;
import pl.ug.recruitment.currency_converter.dto.RegisterComputerRequest;
import pl.ug.recruitment.currency_converter.mapper.ComputerMapper;
import pl.ug.recruitment.currency_converter.webclient.NbpWebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputerService {

    private final ComputerRepository repository;

    private final NbpWebClient nbpWebClient;

    private final WorkingDayService workingDayService;

    private final ComputerMapper computerMapper;

    @Transactional
    public ComputerResponse registerComputer(RegisterComputerRequest request) {
        LocalDate date = request.bookingDate();

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

        return computerMapper.toComputerResponse(savedComputer);
    }

    public List<ComputerResponse> getAllComputers() {
        List<Computer> computers = repository.findAll();

        return computerMapper.toListOfComputerResponse(computers);
    }
}
