package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "price_shows")
@Data
@NoArgsConstructor
@Getter
@Setter
public class PriceShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "price_id", nullable = false)
    private Long price_id;
    @Column(name = "show_id", nullable = false)
    private Long show_id;

}
