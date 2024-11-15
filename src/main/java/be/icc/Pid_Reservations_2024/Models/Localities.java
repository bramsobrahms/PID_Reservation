package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "Localities")
public class Localities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "postal_code", length = 6)
    private String postal_code;
    @Column(name = "locality", length = 60)
    private String locality;

    // Constructor without param
    public Localities() { }

    // Constructor with params
    public Localities(Long id, String postal_code, String locality) {
        this.id = id;
        this.postal_code = postal_code;
        this.locality = locality;
    }

    // toString for locality and postalCode
    @Override
    public String toString() {
        return "Localities{" +
                "postal_code='" + postal_code + '\'' +
                ", locality='" + locality + '\'' +
                '}';
    }

}
