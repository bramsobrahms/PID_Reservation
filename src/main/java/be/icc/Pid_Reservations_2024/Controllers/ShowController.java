package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Models.Price;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShowController {

    @Autowired
    private ShowService showService;

    @GetMapping("/shows")
    public String shows(Model model) {
        List<Show> shows = showService.getAllShows();
        Map<Long, List<String>> showMapSchedules = new HashMap<>();

        for(Show show : shows) {
            Location location = show.getLocation();

            List<String> formattedSchedules = showService.getShowRepresentation(show, location);
            showMapSchedules.put(show.getId(), formattedSchedules);

            List<Price> prices = show.getPrices();
            show.setPrices(prices);
        }

        model.addAttribute("shows", shows);
        model.addAttribute("showMapSchedules", showMapSchedules);
        model.addAttribute("thetitle", "List of Shows");

        return "Show/index";
    }
}
