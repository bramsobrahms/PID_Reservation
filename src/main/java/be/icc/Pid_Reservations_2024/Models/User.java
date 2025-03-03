package be.icc.Pid_Reservations_2024.Models;

import be.icc.Pid_Reservations_2024.Enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="users")
@Data
@NoArgsConstructor
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login", unique = true, nullable = false, length = 30)
    @NotEmpty(message = "The pseudo must no be empty")
    private String login;
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "The password must no be empty")
    private String password;
    @Column(name = "firstname", nullable = false, length = 60)
    @NotEmpty(message = "The firstname must no be empty")
    private String firstName;
    @Column(name = "lastname", nullable = false,  length = 60)
    @NotEmpty(message = "The lastname must no be empty")
    private String lastName;
    @Column(name = "email", nullable = false)
    @NotEmpty(message = "The email must no be empty")
    private String email;
    @Column(name = "language", length = 2)
    private String language;
    @Enumerated(EnumType.STRING)
    private Roles role;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Relation One To Many
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Review> reviews;

    // Constructor with params
    public User(Long id, String login, String password, String firstName, String lastName, String email, String language, Roles role, LocalDateTime createdAt) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.language = language;
        this.role = role;
        this.createdAt = createdAt;
    }

    // toString with some params
    @Override
    public String toString() {
        return "Users{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", language='" + language + '\'' +
                ", language='" + role + '\'' +
                '}';
    }

}
