package pl.ug.recruitment.currency_converter.computer;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WorkingDayServiceTest {

    private final WorkingDayService workingDayService = new WorkingDayService();

    @Test
    void shouldReturnTrueWhenDayIsWorkingDay() {
        //given
        LocalDate date = LocalDate.of(2026, 7, 10);
        //when & then
        assertTrue(workingDayService.isWorkingDay(date));
    }

    @Test
    void shouldReturnFalseWhenDayIsSaturday() {
        //given
        LocalDate date = LocalDate.of(2026, 7, 11);
        //when & then
        assertFalse(workingDayService.isWorkingDay(date));
    }

    @Test
    void shouldReturnFalseWhenDayOfDateIsSunday() {
        //given
        LocalDate date = LocalDate.of(2026, 7, 12);
        //when & then
        assertFalse(workingDayService.isWorkingDay(date));
    }

}