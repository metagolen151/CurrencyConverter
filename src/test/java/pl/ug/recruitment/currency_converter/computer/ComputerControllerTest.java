package pl.ug.recruitment.currency_converter.computer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import pl.ug.recruitment.currency_converter.TestIntegrationConfig;
import pl.ug.recruitment.currency_converter.computer.dto.RegisterComputerRequest;
import pl.ug.recruitment.currency_converter.fixture.ComputerFixture;
import pl.ug.recruitment.currency_converter.fixture.RegisterComputerRequestFixture;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ComputerControllerTest extends TestIntegrationConfig {

    private static final String COMPUTERS_URL = "/api/v1/computers";

    @Autowired
    protected ComputerRepository computerRepository;

    @AfterEach
    void cleanUp() {
        computerRepository.deleteAll();
    }

    @Test
    void shouldRegisterComputerSuccessfully() throws Exception {
        RegisterComputerRequest request = new RegisterComputerRequestFixture().build();

        mockMvc.perform(post(COMPUTERS_URL)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("ACER Aspire"))
                .andExpect(jsonPath("$.bookingDate").value("2026-07-13"))
                .andExpect(jsonPath("$.costUsd").value(345))
                .andExpect(jsonPath("$.costPln").value(1305.69));
    }

    @Test
    void shouldRegisterComputerSuccessfullyWhenDayIsWeekend() throws Exception {
        RegisterComputerRequest request = new RegisterComputerRequestFixture().withBookingDate(LocalDate.of(2026, 7, 12)).build();

        mockMvc.perform(post(COMPUTERS_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("ACER Aspire"))
                .andExpect(jsonPath("$.bookingDate").value("2026-07-12"))
                .andExpect(jsonPath("$.costUsd").value(345))
                .andExpect(jsonPath("$.costPln").value(1311.86));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsBlank() throws Exception {
        RegisterComputerRequest request = new RegisterComputerRequestFixture().withName("").build();

        mockMvc.perform(post(COMPUTERS_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Computer name is required"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsNull() throws Exception {
        RegisterComputerRequest request = new RegisterComputerRequestFixture().withName(null).build();

        mockMvc.perform(post(COMPUTERS_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Computer name is required"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsTooLong() throws Exception {
        RegisterComputerRequest request = new RegisterComputerRequestFixture().withName("a".repeat(101)).build();

        mockMvc.perform(post(COMPUTERS_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Computer name cannot exceed 100 characters"));
    }

    @Test
    void shouldReturnBadRequestWhenDateIsNull() throws Exception {
        RegisterComputerRequest request = new RegisterComputerRequestFixture().withBookingDate(null).build();

        mockMvc.perform(post(COMPUTERS_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Booking date is required"));
    }

    @Test
    void shouldReturnBadRequestWhenCostUsdIsNull() throws Exception {
        RegisterComputerRequest request = new RegisterComputerRequestFixture().withCostUsd(null).build();

        mockMvc.perform(post(COMPUTERS_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("USD cost is required"));
    }

    @Test
    void shouldReturnBadRequestWhenCostUsdIsNegative() throws Exception {
        RegisterComputerRequest request = new RegisterComputerRequestFixture().withCostUsd(new BigDecimal("-345")).build();

        mockMvc.perform(post(COMPUTERS_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("USD cost must be greater than zero"));
    }

    @Test
    void shouldReturnBadRequestWhenCostUsdIsInvalid() throws Exception {
        String request = """
            {
                "name": "ACER Aspire",
                "bookingDate": "2026-07-12",
                "costUsd": "test"
            }
            """;

        mockMvc.perform(post(COMPUTERS_URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request body"));
    }

    @Test
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        String request = """
            {
                "name": "ACER Aspire",
                "bookingDate": "2026-07-12"
            """;

        mockMvc.perform(post(COMPUTERS_URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request body"));
    }

    @Test
    void shouldReturnComputers() throws Exception {
        Computer computer1 = new ComputerFixture()
                .withId(null)
                .build();

        Computer computer2 = new ComputerFixture()
                .withId(null)
                .withName("DELL Latitude")
                .withBookingDate(LocalDate.of(2026, 7, 12))
                .withCostUsd(new BigDecimal("543"))
                .withCostPln(new BigDecimal("2064.76"))
                .build();

        Computer computer3 = new ComputerFixture()
                .withId(null)
                .withName("HP Victus")
                .withBookingDate(LocalDate.of(2026, 7, 15))
                .withCostUsd(new BigDecimal("346"))
                .withCostPln(new BigDecimal("1310.61"))
                .build();

        computerRepository.saveAll(List.of(computer1, computer2, computer3));

        mockMvc.perform(get(COMPUTERS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3))
                .andExpect(jsonPath("$.content[0].name").value("ACER Aspire"))
                .andExpect(jsonPath("$.content[0].bookingDate").value("2026-07-03"))
                .andExpect(jsonPath("$.content[0].costUsd").value(345))
                .andExpect(jsonPath("$.content[0].costPln").value(1290.99))
                .andExpect(jsonPath("$.content[1].name").value("DELL Latitude"))
                .andExpect(jsonPath("$.content[1].bookingDate").value("2026-07-12"))
                .andExpect(jsonPath("$.content[1].costUsd").value(543))
                .andExpect(jsonPath("$.content[1].costPln").value(2064.76))
                .andExpect(jsonPath("$.content[2].name").value("HP Victus"))
                .andExpect(jsonPath("$.content[2].bookingDate").value("2026-07-15"))
                .andExpect(jsonPath("$.content[2].costUsd").value(346))
                .andExpect(jsonPath("$.content[2].costPln").value(1310.61));
    }

    @Test
    void shouldReturnEmptyPageWhenThereAreNoComputers() throws Exception {
        mockMvc.perform(get(COMPUTERS_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(0))
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(0));
    }

    @Test
    void shouldFilterComputersByNameAndBookingDate() throws Exception {
        Computer computer1 = new ComputerFixture()
                .withId(null)
                .build();

        Computer computer2 = new ComputerFixture()
                .withId(null)
                .withName("DELL Latitude")
                .withBookingDate(LocalDate.of(2026, 7, 12))
                .build();

        Computer computer3 = new ComputerFixture()
                .withId(null)
                .withName("DELL Inspiron")
                .withBookingDate(LocalDate.of(2026, 7, 15))
                .build();

        computerRepository.saveAll(List.of(computer1, computer2, computer3));

        mockMvc.perform(get(COMPUTERS_URL)
                        .param("name", "dell")
                        .param("bookingDate", "2026-07-12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].name").value("DELL Latitude"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void shouldSortComputersByNameDescending() throws Exception {
        Computer computer1 = new ComputerFixture()
                .withId(null)
                .build();

        Computer computer2 = new ComputerFixture()
                .withId(null)
                .withName("DELL Latitude")
                .build();

        Computer computer3 = new ComputerFixture()
                .withId(null)
                .withName("HP Victus")
                .build();

        computerRepository.saveAll(List.of(computer1, computer2, computer3));

        mockMvc.perform(get(COMPUTERS_URL)
                        .param("sort", "name,desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("HP Victus"))
                .andExpect(jsonPath("$.content[1].name").value("DELL Latitude"))
                .andExpect(jsonPath("$.content[2].name").value("ACER Aspire"));
    }

    @Test
    void shouldReturnPaginatedComputers() throws Exception {
        Computer computer1 = new ComputerFixture()
                .withId(null)
                .build();

        Computer computer2 = new ComputerFixture()
                .withId(null)
                .withName("DELL Latitude")
                .build();

        Computer computer3 = new ComputerFixture()
                .withId(null)
                .withName("HP Victus")
                .build();

        computerRepository.saveAll(List.of(computer1, computer2, computer3));

        mockMvc.perform(get(COMPUTERS_URL)
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("ACER Aspire"))
                .andExpect(jsonPath("$.content[1].name").value("DELL Latitude"))
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.totalPages").value(2));
    }
}