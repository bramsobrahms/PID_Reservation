package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "localities")
@Data
@NoArgsConstructor
@Getter @Setter
public class Locality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "postal_code", length = 6)
    private String postalCode;
    @Column(name = "locality", length = 60)
    private String locality;

    // Relation One To Many
    @OneToMany(mappedBy = "locality")
    private List<Location> locations;

    // Constructor with params
    public Locality(String postal_code, String locality) {
        this.postalCode = postal_code;
        this.locality = locality;
    }

    // toString for locality and postalCode
    @Override
    public String toString() {
        return "Localities{" +
                "postal_code='" + postalCode + '\'' +
                ", locality='" + locality + '\'' +
                '}';
    }

}
