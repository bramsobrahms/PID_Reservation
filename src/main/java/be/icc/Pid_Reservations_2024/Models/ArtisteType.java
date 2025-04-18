package be.icc.Pid_Reservations_2024.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table( name = "artiste_types")
@Data @NoArgsConstructor
@Getter @Setter
public class ArtisteType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relation Many To One
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "artist_id", referencedColumnName = "id", nullable = false)
    private Artist artist;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private Type type;

    // Relation Many To Many
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference("show-artist-type")
    @JoinTable(
            name = "artiste_type_shows",
            joinColumns = @JoinColumn(name = "artiste_type_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id")
    )
    List<Show> shows;

    // ToString
    @Override
    public String toString() {
        return "Artiste_Type{" +
                "id=" + id +
                ", artist=" + artist +
                ", type=" + type +
                '}';
    }
}
