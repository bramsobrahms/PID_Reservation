package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artists;
import be.icc.Pid_Reservations_2024.Services.ArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/artist/create")
    public String create(Model model) {
        Artists artist = new Artists("1900-01-01",null,null);

        model.addAttribute("artist",artist);

        return "artist/create";
    }

    @PostMapping("/artist/create")
    public String create(@Valid @ModelAttribute("artist") Artists artist, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "Artist/create";
        }

        artistService.addArtist(artist);

        return "redirect:/artist/"+artist.getId();
    }

}
