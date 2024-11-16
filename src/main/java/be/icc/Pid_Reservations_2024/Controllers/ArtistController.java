package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artists;
import be.icc.Pid_Reservations_2024.Services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArtistController {

    @Autowired
    ArtistService artistService;

    @GetMapping("/artists")
    public String index(Model model) {
        List<Artists> artists = artistService.getAllArtists();

        model.addAttribute("artists", artists);
        model.addAttribute("title", "List of artists");

        return "Artist/index";
    }
}
