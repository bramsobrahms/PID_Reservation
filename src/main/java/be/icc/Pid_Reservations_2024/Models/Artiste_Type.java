package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter @Setter
@Table( name = "Artiste_Type")
public class Artiste_Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relation One To Many
    @OneToMany(mappedBy = "artist_type")
    private Set<Artiste_Type_Show> artiste_type_show;

    // Constructor by default
    protected Artiste_Type() {}

}
