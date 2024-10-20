package PID_Reservation.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "Artists")
public class Artists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lastname", length = 60)
    private String lastname;
    @Column(name = "firstname", length = 60)
    private String firstname;
    @Column(name = "birthdate", length = 150)
    private LocalDate birthdate;

    // Constructor by default
    public Artists() { }

    // Constructor with params
    public Artists(String birthdate, String firstname, String lastname, Long id) {
        this.birthdate = LocalDate.parse(birthdate);
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = id;
    }

    // ToString
    @Override
    public String toString() {
        return "Artists{" +
                ", lastname= '" + lastname + '\'' +
                ", firstname= '" + firstname + '\'' +
                ", birthdate=' " + birthdate + '\'' +
                '}';
    }
}
