package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artists;
import be.icc.Pid_Reservations_2024.Services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    @GetMapping("/artist/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Artists artist = artistService.getArtist(id);

        model.addAttribute("artist",artist);
        model.addAttribute("title", "Profile of an artist");

        return "Artist/show";
    }

}
