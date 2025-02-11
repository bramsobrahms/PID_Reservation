package be.icc.Pid_Reservations_2024.api.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artist;
import be.icc.Pid_Reservations_2024.Repositories.ArtistRepository;
import be.icc.Pid_Reservations_2024.api.Hateoas.ArtistModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class ArtistApiController {

    private final ArtistRepository artistRepository;
    private final ArtistModelAssembler artistAssembler;

    public ArtistApiController(ArtistRepository artistRepository, ArtistModelAssembler artistAssembler) {
        this.artistRepository = artistRepository;
        this.artistAssembler = artistAssembler;
    }

    @GetMapping("/artists")
    public CollectionModel<EntityModel<Artist>> allArtists() {
        List<EntityModel<Artist>> artists = artistRepository.findAll() // Get all artists from DB
                .stream() // Convert the list of artists into a stream to apply functional operations
                .map(artistAssembler::toModel) // Convert each `Artist` object into an `EntityModel<Artist> added HATEOAS links`
                .toList(); // Collect the results into a list

        // Create a Collection with the list of artists and add a HATEOAS link to the "/artists" endpoint
        return CollectionModel.of(artists, // The list of 'EntityModel<Artist>' objects
                linkTo(methodOn(ArtistApiController.class).allArtists()) // Create a link to the 'allArtists()' method in the same controller
                        .withSelfRel());  // Add a 'self' link to this resource (refers to itself)
    }

    @GetMapping("/artist/{id}")
    public EntityModel<Artist> anArtist(@PathVariable long id) {
        Artist artist = artistRepository.findById(id).orElse(null);
        return artistAssembler.toModel(artist);
    }

    @PostMapping("/admin/artist")
    public ResponseEntity<?> newArtist(@RequestBody Artist newArtist) {
        Artist saveNewArtist = artistRepository.save(newArtist);
        return ResponseEntity.created(linkTo(methodOn(ArtistApiController.class)
                .anArtist(saveNewArtist.getId())) // Pass the ID of the saved artist
                .toUri())  // Convert the link into a URI format
                .body(saveNewArtist); // Include the saved artist object in the response body
    }

    @PutMapping("/admin/artist/{id}")
    public ResponseEntity<?> updateArtist(@RequestBody Artist updatedArtist, @PathVariable long id) {
        Artist updateTheArtist = artistRepository.findById(id)
                .map(artist -> { // If the artist exists, update their information
                    artist.setFirstname(updatedArtist.getFirstname());
                    artist.setLastname(updatedArtist.getLastname());
                    return artistRepository.save(artist);
                }).orElseGet(() -> { // If the artist doesn't exist, create a new one
                    updatedArtist.setId(id); // Set the ID for the new artist
                    return artistRepository.save(updatedArtist);
                });

        return ResponseEntity.created( // Indicates that the resource was created successfully
                linkTo(methodOn(ArtistApiController.class) // Creates a link to the "anArtist" method for the updated artist
                .anArtist(updateTheArtist.getId())) // Pass the ID of the updated artis
                .toUri()) // Convert the link into a URI format
                .body(artistAssembler.toModel(updateTheArtist)); // Return the updated artist as a HATEOAS model
    }

    @DeleteMapping("/admin/artist/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable long id) {
        artistRepository.deleteById(id);
        // Return a ResponseEntity with a "no content" status (204), indicating the deletion was successful
        return ResponseEntity.noContent().build();
    }
}
