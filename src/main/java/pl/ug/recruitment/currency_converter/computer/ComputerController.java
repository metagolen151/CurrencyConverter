package pl.ug.recruitment.currency_converter.computer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ug.recruitment.currency_converter.dto.ComputerResponse;
import pl.ug.recruitment.currency_converter.dto.RegisterComputerRequest;

import java.util.List;

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
    public ResponseEntity<List<ComputerResponse>> getAllComputers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(computerService.getAllComputers());
    }
}
