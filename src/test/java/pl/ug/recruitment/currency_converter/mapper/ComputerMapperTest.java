package pl.ug.recruitment.currency_converter.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.ug.recruitment.currency_converter.computer.Computer;
import pl.ug.recruitment.currency_converter.computer.dto.ComputerResponse;
import pl.ug.recruitment.currency_converter.computer.dto.RegisterComputerRequest;
import pl.ug.recruitment.currency_converter.fixture.ComputerFixture;
import pl.ug.recruitment.currency_converter.fixture.ComputerResponseFixture;
import pl.ug.recruitment.currency_converter.fixture.RegisterComputerRequestFixture;
import pl.ug.recruitment.currency_converter.xml.dto.ComputerXml;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ComputerMapperTest {

    private final ComputerMapper mapper = Mappers.getMapper(ComputerMapper.class);

    @Test
    void shouldMapRegisterComputerRequestToComputer() {
        //given
        RegisterComputerRequest request = new RegisterComputerRequestFixture().build();
        //when
        Computer computer = mapper.toComputer(request);
        //then
        assertEquals(request.name(), computer.getName());
        assertEquals(request.bookingDate(), computer.getBookingDate());
        assertEquals(request.costUsd(), computer.getCostUsd());
    }

    @Test
    void shouldReturnNullWhenRegisterComputerRequestIsNull() {
        //when
        Computer computer = mapper.toComputer(null);
        //then
        assertNull(computer);
    }

    @Test
    void shouldMapComputerToComputerResponse() {
        //given
        Computer computer = new ComputerFixture().build();
        //when
        ComputerResponse response = mapper.toComputerResponse(computer);
        //then
        assertEquals(computer.getName(), response.name());
        assertEquals(computer.getBookingDate(), response.bookingDate());
        assertEquals(computer.getCostUsd(), response.costUsd());
        assertEquals(computer.getCostPln(), response.costPln());
    }

    @Test
    void shouldReturnNullWhenComputerIsNull() {
        //when
        ComputerResponse response = mapper.toComputerResponse(null);
        //then
        assertNull(response);
    }

    @Test
    void shouldMapListOfComputersToListOfComputersResponse() {
        //given
        Computer computer1 = new ComputerFixture().build();
        Computer computer2 = new ComputerFixture()
                .withId(UUID.randomUUID())
                .withName("DELL Latitude")
                .withBookingDate(LocalDate.of(2026, 7, 12))
                .withCostUsd(new BigDecimal("543"))
                .withCostPln(new BigDecimal("2064.76"))
                .build();
        Computer computer3 = new ComputerFixture()
                .withId(UUID.randomUUID())
                .withName("HP Victus")
                .withBookingDate(LocalDate.of(2026, 7, 15))
                .withCostUsd(new BigDecimal("346"))
                .withCostPln(new BigDecimal("1310.61"))
                .build();

        List<Computer> computers = List.of(computer1, computer2, computer3);
        //when
        List<ComputerResponse> computersResponse = mapper.toListOfComputerResponse(computers);
        //then
        assertEquals(3, computersResponse.size());

        assertEquals(computer1.getId(), computersResponse.get(0).id());
        assertEquals(computer1.getName(), computersResponse.get(0).name());
        assertEquals(computer1.getBookingDate(), computersResponse.get(0).bookingDate());
        assertEquals(computer1.getCostUsd(), computersResponse.get(0).costUsd());
        assertEquals(computer1.getCostPln(), computersResponse.get(0).costPln());

        assertEquals(computer2.getId(), computersResponse.get(1).id());
        assertEquals(computer2.getName(), computersResponse.get(1).name());
        assertEquals(computer2.getBookingDate(), computersResponse.get(1).bookingDate());
        assertEquals(computer2.getCostUsd(), computersResponse.get(1).costUsd());
        assertEquals(computer2.getCostPln(), computersResponse.get(1).costPln());

        assertEquals(computer3.getId(), computersResponse.get(2).id());
        assertEquals(computer3.getName(), computersResponse.get(2).name());
        assertEquals(computer3.getBookingDate(), computersResponse.get(2).bookingDate());
        assertEquals(computer3.getCostUsd(), computersResponse.get(2).costUsd());
        assertEquals(computer3.getCostPln(), computersResponse.get(2).costPln());
    }

    @Test
    void shouldReturnNullWhenListOfComputersIsNull() {
        //when
        List<ComputerResponse> computersResponse = mapper.toListOfComputerResponse(null);
        //then
        assertNull(computersResponse);
    }

    @Test
    void shouldMapComputerResponseToComputerXml() {
        // given
        ComputerResponse response = new ComputerResponseFixture().build();
        // when
        ComputerXml result = mapper.toComputerXml(response);
        // then
        assertEquals(response.name(), result.name());
        assertEquals(response.bookingDate(), result.bookingDate());
        assertEquals(response.costUsd(), result.costUsd());
        assertEquals(response.costPln(), result.costPln());
    }

    @Test
    void shouldReturnNullWhenComputerResponseIsNull() {
        // when
        ComputerXml result = mapper.toComputerXml(null);
        // then
        assertNull(result);
    }
}