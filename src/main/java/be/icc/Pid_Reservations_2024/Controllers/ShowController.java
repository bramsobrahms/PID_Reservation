package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Models.Price;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShowController {

    @Autowired
    private ShowService showService;

    /**
     * @param page  the current page number
     * @param size  the number of shows per page
     * @param model the model used to send data to the view
     * @return the name of the view to display
     */
    @GetMapping("/shows")
    public String shows(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
        // Create pagination object
        Pageable pageable = PageRequest.of(page, size);
        // Get the shows for the current page
        Page<Show> showPage = showService.getAllShows(pageable);

        Map<Long, List<String>> showMapSchedules = new HashMap<>(); // To store schedule each show

        for (Show show : showPage.getContent()) {
            Location location = show.getLocation();

            // Get formatted schedule for the show
            List<String> formattedSchedules = showService.getShowRepresentation(show, location);
            showMapSchedules.put(show.getId(), formattedSchedules); // Add schedule to the Map

            List<Price> prices = show.getPrices(); // Get the price for the show
            show.setPrices(prices); // Add the price to Show
        }

        // Add All necessary data to the model to be used in the view
        model.addAttribute("shows", showPage);
        model.addAttribute("showMapSchedules", showMapSchedules);
        model.addAttribute("thetitle", "List of Shows");

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", showPage.getTotalPages());

        return "Show/index";
    }
}
