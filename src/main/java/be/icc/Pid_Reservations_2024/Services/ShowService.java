package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Repositories.RepresentationRepository;
import be.icc.Pid_Reservations_2024.Repositories.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private RepresentationRepository representationRepository;

    /**
     * Gets a list of shows in pages.
     * <p>
     * This method gets shows from the database in a paginated way.
     * It returns only a part of the shows based on the page number size.
     *
     * @param pageable information about the page( how many shows per page and wich page to get)
     * @return a {@link Page} with a list of {@link Show} objects for the current page.
     */
    public Page<Show> getAllShows(Pageable pageable) {
        return showRepository.findAll(pageable);
    }

    /**
     * Gets a list of show schedules in a simple format.
     * <p>
     * This method gets the schedules of a show at a specific location.
     * It formats each schedule into a readable string, like "Le 19 mars à 17:50"
     *
     * @return a list of string with the formatted schedule strings.
     */
    public List<String> getShowRepresentation(Show show, Location location) {
        // List<LocalDateTime> cause the query stock only format dates and hours
        List<LocalDateTime> schedules = representationRepository.findShowAndLocationById(show, location);

        // Format date and time as I would like
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Le ' dd MMMM yyyy ' à ' HH:mm");
        return schedules.stream()
                .map(schedule -> schedule.format(formatter))// Format each schedule
                .collect(Collectors.toList()); // Regroups the formatted schedules into a list
    }

}
