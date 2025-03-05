package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShowController {

    @Autowired
    private ShowService showService;

    @GetMapping("/shows")
    public String shows(Model model) {
        List<Show> shows = showService.getAllShows();

        model.addAttribute("shows", shows);
        model.addAttribute("thetitle", "List of Shows");

        return "Show/index";
    }
}
