package pl.ug.recruitment.currency_converter.computer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.ug.recruitment.currency_converter.computer.dto.ComputerResponse;
import pl.ug.recruitment.currency_converter.computer.dto.RegisterComputerRequest;
import pl.ug.recruitment.currency_converter.fixture.ComputerFixture;
import pl.ug.recruitment.currency_converter.fixture.ComputerResponseFixture;
import pl.ug.recruitment.currency_converter.fixture.RegisterComputerRequestFixture;
import pl.ug.recruitment.currency_converter.mapper.ComputerMapper;
import pl.ug.recruitment.currency_converter.webclient.NbpWebClient;
import pl.ug.recruitment.currency_converter.xml.XmlService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComputerServiceTest {

    @Mock
    private ComputerRepository repository;

    @Mock
    private NbpWebClient nbpWebClient;

    @Mock
    private WorkingDayService workingDayService;

    @Mock
    private ComputerMapper computerMapper;

    @Mock
    private XmlService xmlService;

    @InjectMocks
    private ComputerService computerService;

    @Test
    void shouldRegisterComputer() {
        //given
        RegisterComputerRequest request = new RegisterComputerRequestFixture().build();
        ComputerResponse response = new ComputerResponseFixture().build();
        Computer computer = new ComputerFixture().build();

        LocalDate date = request.bookingDate();
        BigDecimal rate = new BigDecimal("3.654");

        when(workingDayService.isWorkingDay(date)).thenReturn(true);
        when(computerMapper.toComputer(request)).thenReturn(computer);
        when(nbpWebClient.getExchangeRateForDate(date)).thenReturn(rate);
        when(repository.save(computer)).thenReturn(computer);
        when(computerMapper.toComputerResponse(computer)).thenReturn(response);
        //when
        ComputerResponse result = computerService.registerComputer(request);
        //then
        assertEquals(response, result);
        assertEquals(request.costUsd().multiply(rate).setScale(2, RoundingMode.HALF_UP),
                computer.getCostPln());
    }

    @Test
    void shouldRegisterComputerWhenBookingDateIsWeekend() {
        //given
        RegisterComputerRequest request = new RegisterComputerRequestFixture().withBookingDate(LocalDate.of(2026, 7, 12)).build();
        ComputerResponse response = new ComputerResponseFixture().build();
        Computer computer = new ComputerFixture().build();

        LocalDate date = request.bookingDate();
        LocalDate previousWorkingDay = date.minusDays(2);
        BigDecimal rate = new BigDecimal("3.654");

        when(workingDayService.isWorkingDay(date)).thenReturn(false);
        when(workingDayService.isWorkingDay(date.minusDays(1))).thenReturn(false);
        when(workingDayService.isWorkingDay(previousWorkingDay)).thenReturn(true);
        when(computerMapper.toComputer(request)).thenReturn(computer);
        when(nbpWebClient.getExchangeRateForDate(previousWorkingDay)).thenReturn(rate);
        when(repository.save(computer)).thenReturn(computer);
        when(computerMapper.toComputerResponse(computer)).thenReturn(response);
        //when
        ComputerResponse result = computerService.registerComputer(request);
        //then
        assertEquals(response, result);
        assertEquals(request.costUsd().multiply(rate).setScale(2, RoundingMode.HALF_UP),
                computer.getCostPln());
    }

    @Test
    void shouldGetAllComputers() {
        // given
        String name = "acer";
        LocalDate bookingDate = LocalDate.of(2026, 7, 3);
        Pageable pageable = PageRequest.of(0, 5);

        Computer computer = new ComputerFixture().build();
        ComputerResponse response = new ComputerResponseFixture().build();

        Page<Computer> computers = new PageImpl<>(List.of(computer));

        when(repository.search(name, bookingDate, pageable)).thenReturn(computers);
        when(computerMapper.toComputerResponse(computer)).thenReturn(response);

        // when
        Page<ComputerResponse> result = computerService.getAllComputers(name, bookingDate, pageable);

        // then
        assertEquals(1, result.getTotalElements());
        assertEquals(response, result.getContent().getFirst());
    }

    @Test
    void shouldReturnEmptyPageWhenNoComputersFound() {
        // given
        String name = "unknown";
        LocalDate bookingDate = null;
        Pageable pageable = PageRequest.of(0, 5);

        when(repository.search(name, bookingDate, pageable)).thenReturn(Page.empty());

        // when
        Page<ComputerResponse> result = computerService.getAllComputers(name, bookingDate, pageable);

        // then
        assertTrue(result.isEmpty());
    }
}