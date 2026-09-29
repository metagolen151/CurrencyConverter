package pl.ug.recruitment.currency_converter.computer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComputerRepository extends JpaRepository<Computer, UUID> {

}
