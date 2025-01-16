package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "artists")
@Data
@NoArgsConstructor
@Getter @Setter
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lastname", length = 60)
    @NotEmpty(message = "The lastname must no be empty")
    @Size(min=2, max=60, message = "The lastname must be between 2 and 60 characters")
    private String lastname;
    @Column(name = "firstname", length = 60)
    @NotEmpty(message = "The firstname must no be empty")
    @Size(min=2, max=60, message = "The firstname must be between 2 and 60 characters")
    private String firstname;

    // Relation One To Many
    @OneToMany(mappedBy = "artist")
    private List<ArtisteType> artisteTypes;

    // Constructor with params
    public Artist(String birthdate, String firstname, String lastname, Long id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = id;
    }

    public Artist(String birthdate, String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // ToString
    @Override
    public String toString() {
        return "Artist{" +
                ", lastname= '" + lastname + '\'' +
                ", firstname= '" + firstname + '\'' +
                '}';
    }

}
