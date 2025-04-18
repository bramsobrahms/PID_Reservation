package be.icc.Pid_Reservations_2024.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.slugify.Slugify;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shows")
@Data
@NoArgsConstructor
@Getter @Setter
public class Show {

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
    @Column(name = "isBookable", columnDefinition = "TINYINT")
    private Boolean isBookable;

    // Relation One to Many
    @OneToMany(targetEntity = Representation.class, mappedBy = "show", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference("show-representation")
    private List<Representation> representations = new ArrayList<>();

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("show-review")
    private List<Review> reviews;

    // Relation Many To One
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference("location-show")
    private Location location;

    // Relation Many To Many
    @ManyToMany(mappedBy = "shows", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonIgnore
    List<Price> prices;

    @ManyToMany(mappedBy = "shows", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonIgnore
    List<ArtisteType> artisteTypes;

    // Constructor with params
    public Show(String title, String posterUrl, Date created_in, Boolean isBookable) {
        Slugify slg = Slugify.builder().build();

        this.slug = slg.slugify(title);
        this.title = title;
        this.posterUrl = posterUrl;
        this.created_in = created_in;
        this.isBookable = isBookable;
    }

    // ToString
    @Override
    public String toString() {
        return "Shows{" +
                "bookable=" + isBookable +
                ", created_in=" + created_in +
                ", posterUrl='" + posterUrl + '\'' +
                ", slug='" + slug + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", locations=" + location +
                '}';
    }
}
