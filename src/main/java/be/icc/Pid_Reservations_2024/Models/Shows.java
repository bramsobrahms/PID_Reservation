package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "Shows")
public class Shows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "slug", unique = true, length = 60)
    private String slug;
    @Column(name = "title")
    private String title;
    @Column(name = "poster_url")
    private String posterUrl;
    @Column(name = "duration", length = 5, columnDefinition = "SMALLINT UNSIGNED")
    private Integer duration;
    @Column(name = "created_in")
    private Date created_in;
    @Column(name = "bookable", columnDefinition = "TINYINT")
    private Boolean bookable;

    // Relation One to Many
    @OneToMany(mappedBy = "shows")
    private Set<Artiste_Type_Show> artiste_type_show;

    @OneToMany(mappedBy = "shows")
    private Set<Representations> representations;

    @OneToMany(mappedBy = "shows")
    private Set<Reviews> reviews;

    // Relation Many To One
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Locations locations;

    // Constructor by default
    public Shows() {}
}
