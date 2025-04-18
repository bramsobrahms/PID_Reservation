package be.icc.Pid_Reservations_2024.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "types")
@Data
@NoArgsConstructor
@Getter @Setter
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type", length = 60)
    @NotBlank(message = "The type must not be empty")
    @Size(max=60, message = "The type must be 60 characters")
    private String type;

    // Relation One to Many
    @OneToMany(mappedBy = "type",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ArtisteType> artiste_types;

    // Constructor with params
    public Type(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    // Constructor without id
    public Type(String type) {
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
