package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Models.Representation;
import be.icc.Pid_Reservations_2024.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RepresentationRepository extends JpaRepository<Representation, Long> {

    @Query("SELECT r.schedule FROM Representation r WHERE r.show = :show AND r.locations = :location ")
    List<LocalDateTime> findShowAndLocationById(Show show, Location location);
}
