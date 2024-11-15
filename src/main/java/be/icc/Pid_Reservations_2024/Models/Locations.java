package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "Locations")
public class Locations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "slug", unique = true, length = 60)
    private String slug;
    @Column(name = "designation", length = 60)
    private String designation;
    @Column(name = "address")
    private String address;
    @Column(name = "website")
    private String website;
    @Column(name = "phone", length = 30)
    private String phone;

    // Relation One To Many
    @OneToMany(mappedBy = "locations")
    private Set<Representations> representations;

    @OneToMany(mappedBy = "locations")
    private Set<Shows> shows;


    // Constructor by default
    protected Locations() {}
}
