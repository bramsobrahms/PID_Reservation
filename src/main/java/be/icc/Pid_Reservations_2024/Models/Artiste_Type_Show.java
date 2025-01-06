package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Artiste_Type_Show")
@Data
@NoArgsConstructor
@Getter @Setter
public class Artiste_Type_Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relation Many to One
    @ManyToOne
    @JoinColumn(name = "artist_type_id", referencedColumnName = "id", nullable = false)
    private Artiste_Type artist_type;

    @ManyToOne
    @JoinColumn(name = "show_id", referencedColumnName = "id", nullable = false)
    private Shows shows;

}
