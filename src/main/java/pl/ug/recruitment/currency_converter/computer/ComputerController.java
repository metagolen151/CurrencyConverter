package pl.ug.recruitment.currency_converter.computer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ug.recruitment.currency_converter.computer.dto.ComputerResponse;
import pl.ug.recruitment.currency_converter.computer.dto.RegisterComputerRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/computers")
@RequiredArgsConstructor
public class ComputerController {

    private final ComputerService computerService;

    @PostMapping
    public ResponseEntity<ComputerResponse> registerComputer(@Valid @RequestBody RegisterComputerRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(computerService.registerComputer(request));
    }

    @GetMapping
    public ResponseEntity<Page<ComputerResponse>> getAllComputers(
            Pageable pageable,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) LocalDate bookingDate
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(computerService.getAllComputers(
                        name,
                        bookingDate,
                        pageable
                ));
    }
}
