package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "Representations")
public class Representations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "schedule")
    private LocalDateTime schedule;

    // Constructor by default
    protected Representations() {}

}
