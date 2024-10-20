package PID_Reservation.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "Types")
public class Types {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type", length = 60)
    private String type;

    // Constructor by default
    public Types() {}

    // constructor with param
    public Types(String type) {
        this.type = type;
    }

    // ToString for type
    @Override
    public String toString() {
        return "Types{" +
                "type=' " + type + '\'' +
                '}';
    }
}
