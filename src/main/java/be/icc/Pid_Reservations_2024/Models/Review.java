package be.icc.Pid_Reservations_2024.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@Getter @Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "review", columnDefinition = "TEXT")
    private String review;
    @Column(name = "stars", columnDefinition = "TINYINT UNSIGNED")
    private Short stars;
    @Column(name = "isValidated")
    private Boolean isValidated;
    @Column(name = "create_at")
    private LocalDateTime create_at;
    @Column(name = "update_ad")
    private LocalDateTime update_ad;

    // Relation Many To One
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "show_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference("show-review")
    private Show show;

}
