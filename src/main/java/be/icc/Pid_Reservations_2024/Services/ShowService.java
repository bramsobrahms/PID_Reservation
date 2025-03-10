package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Repositories.RepresentationRepository;
import be.icc.Pid_Reservations_2024.Repositories.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private RepresentationRepository representationRepository;

    public List<Show> getAllShows() {
        List<Show> shows = new ArrayList<>();
        showRepository.findAll().forEach(shows::add);
        return shows;
    }

    public List<String> getShowRepresentation(Show show, Location location) {
        List<LocalDateTime> schedules = representationRepository.findShowAndLocationById(show, location);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Le ' dd MMMM yyyy ' à ' HH:mm");
       return schedules.stream()
               .map(schedule -> schedule.format(formatter))
                       .collect(Collectors.toList());
    }

}
