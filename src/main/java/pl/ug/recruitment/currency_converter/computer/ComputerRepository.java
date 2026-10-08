package pl.ug.recruitment.currency_converter.computer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;

public interface ComputerRepository extends JpaRepository<Computer, UUID> {

    @Query("""
        SELECT c
        FROM Computer c
        WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))
          AND c.bookingDate = COALESCE(:bookingDate, c.bookingDate)
        """)
    Page<Computer> search(
            @Param("name") String name,
            @Param("bookingDate") LocalDate bookingDate,
            Pageable pageable
    );
}
