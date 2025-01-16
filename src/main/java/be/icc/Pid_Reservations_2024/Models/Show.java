package be.icc.Pid_Reservations_2024.Models;

import com.github.slugify.Slugify;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "bookable", columnDefinition = "TINYINT")
    private Boolean bookable;

    // Relation One to Many
    @OneToMany(mappedBy = "show")
    private List<Representation> representations;

    @OneToMany(mappedBy = "show")
    private List<Review> reviews;

    @OneToMany(mappedBy = "show")
    private List<ArtisteType> artiste_type;

    // Relation Many To One
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location location;

    // Relation Many To Many
    @ManyToMany(mappedBy = "shows")
    List<Price> prices;

    // Constructor with params
    public Show(String title, String posterUrl, Date created_in, Boolean bookable) {
        Slugify slg = Slugify.builder().build();

        this.slug = slg.slugify(title);
        this.title = title;
        this.posterUrl = posterUrl;
        this.created_in = created_in;
        this.bookable = bookable;
    }

    // ToString
    @Override
    public String toString() {
        return "Shows{" +
                "bookable=" + bookable +
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
