package be.icc.Pid_Reservations_2024.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "representations")
@Data
@NoArgsConstructor
@Getter @Setter
public class Representation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "schedule")
    private LocalDateTime schedule;

    // Relation One To Many
    @OneToMany(mappedBy = "representation", fetch = FetchType.EAGER)
    @JsonBackReference("")
    private List<RepresentationReservation> representation_reservations;

    // Relation Many to One
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "show_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference("show-representation")
    private Show show;

    // Relation Many to Many
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference("location-representation")
    private Location locations;

    // Constructor with params
    public Representation(Long id, LocalDateTime schedule, Show show) {
        this.id = id;
        this.schedule = schedule;
        this.show = show;
    }

    // ToString
    @Override
    public String toString() {
        return "Representations{" +
                "id=" + id +
                ", schedule=" + schedule +
                ", show=" + show +
                '}';
    }
}
