package PID_Reservation.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "Artiste_type_show")
public class Artiste_Type_Show {

    @Id
    @GeneratedValue
    private Long id;
}
