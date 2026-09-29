package pl.ug.recruitment.currency_converter.computer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "computers")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Computer {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal costUsd;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal costPln;
}
