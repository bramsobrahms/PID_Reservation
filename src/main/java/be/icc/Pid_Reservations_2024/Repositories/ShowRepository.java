package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Long> {

    Optional<Show> findById(long id);

    //List<Show> filterByShow(String show);

    //List<Show> sortByShow(String show);

    //List<Show> searchShow(String show);

}
