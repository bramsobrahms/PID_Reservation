package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@Table(name ="Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login", unique = true, nullable = false, length = 30)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "firstname", nullable = false, length = 60)
    private String firstName;
    @Column(name = "lastname", nullable = false,  length = 60)
    private String lastName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "language", length = 2)
    private String language;
    @Column(name = "role")
    private String role;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Relation One To Many
    @OneToMany(mappedBy = "users")
    private List<Reservations> reservations;

    @OneToMany(mappedBy = "users")
    private List<Role_User> role_user;

    @OneToMany(mappedBy = "users")
    private List<Reviews> reviews;

    // Constructor by default
    protected Users() {}

    // Constructor with params
    public Users(Long id, String login, String password, String firstName, String lastName, String email, String language, String role, LocalDateTime createdAt) {
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
                '}';
    }

}
