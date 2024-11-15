package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "Representations")
public class Representations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "schedule")
    private LocalDateTime schedule;

    // Relation One To Many
    @OneToMany(mappedBy = "representations")
    private Set<Representation_Reservation> representation_reservations;

    // Relation Many to One
    @ManyToOne
    @JoinColumn(name = "show_id", referencedColumnName = "id")
    private Shows show;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Locations locations;

    // Constructor by default
    protected Representations() {}

}
