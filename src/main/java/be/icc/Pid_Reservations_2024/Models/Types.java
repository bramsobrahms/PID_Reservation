package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Types")
@Data
@NoArgsConstructor
@Getter @Setter
public class Types {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type", length = 60)
    @NotBlank(message = "The type must not be empty")
    @Size(max=60, message = "The type must be 60 characters")
    private String type;

    // Relation One to Many
    @OneToMany(mappedBy = "type")
    private List<Artiste_Type> artiste_type;

    // Constructor with params
    public Types(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    // Constructor without id
    public Types(String type) {
        this.type = type;
    }

    // ToString
    @Override
    public String toString() {
        return "Types{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
