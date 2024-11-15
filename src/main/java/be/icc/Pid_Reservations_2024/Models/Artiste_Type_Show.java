package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "Artiste_Type_Show")
public class Artiste_Type_Show {

    private Long id;

    // Constructor by default
    public Artiste_Type_Show() {}

}
