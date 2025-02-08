package be.icc.Pid_Reservations_2024.api.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artist;
import be.icc.Pid_Reservations_2024.Repositories.ArtistRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ArtistApiController {

    private final ArtistRepository artistRepository;

    public ArtistApiController(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @GetMapping("/artists")
    public List<Artist> allArtists() {
        return artistRepository.findAll();
    }

    @GetMapping("/artist/{id}")
    public Artist anArtist(@PathVariable long id) {
        return artistRepository.ApiFindById(id).orElseThrow( () -> new RuntimeException("Artist not found"));
    }

    @PostMapping("/admin/artist")
    public Artist newArtist(@RequestBody Artist newArtist) {
        return artistRepository.save(newArtist);
    }

    @PutMapping("/admin/artist/{id}")
    public Artist updateArtist(@RequestBody Artist updatedArtist, @PathVariable long id) {
        return artistRepository.ApiFindById(id)
                .map(artist -> {
                    artist.setFirstname(updatedArtist.getFirstname());
                    artist.setLastname(updatedArtist.getLastname());
                    return artistRepository.save(artist);
                }).orElseGet(() -> {
                    updatedArtist.setId(id);
                    return artistRepository.save(updatedArtist);
                })
        ;
    }

    @DeleteMapping("/admin/artist/{id}")
    public void deleteArtist(@PathVariable long id) {
        artistRepository.deleteById(id);
    }
}
