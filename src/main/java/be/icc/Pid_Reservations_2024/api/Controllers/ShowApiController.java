package be.icc.Pid_Reservations_2024.api.Controllers;

import be.icc.Pid_Reservations_2024.Config.SpringSecurityConfig;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Repositories.ShowRepository;
import be.icc.Pid_Reservations_2024.api.Dto.ShowIdDto;
import be.icc.Pid_Reservations_2024.api.Dto.ShowsDto;
import be.icc.Pid_Reservations_2024.api.Hateoas.showModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class ShowApiController {

    private final ShowRepository showRepository;
    private final showModelAssembler showAssembler;

    public ShowApiController(ShowRepository showRepository, showModelAssembler showAssembler) {
        this.showRepository = showRepository;
        this.showAssembler = showAssembler;
    }


    @GetMapping("/shows")
    public CollectionModel<EntityModel<Show>> allShows() {
        List<EntityModel<Show>> shows = showRepository.findAll() // Get all artists from DB
                .stream() // Convert the list of artists into a stream to apply functional operations
                .map(showAssembler::toModel) // Convert each `Artist` object into an `EntityModel<Artist> added HATEOAS links`
                .toList(); // Collect the results into a list

        // Create a Collection with the list of artists and add a HATEOAS link to the "/artists" endpoint
        return CollectionModel.of(shows, // The list of 'EntityModel<Artist>' objects
                linkTo(methodOn(ShowApiController.class).allShows()) // Create a link to the 'allArtists()' method in the same controller
                        .withRel("All shows"));  // Add a 'self' link to this resource (refers to itself)
    }

    @GetMapping("/show/{id}")
    public EntityModel<Show> aShow(@PathVariable long id) {
        Show show = showRepository.findById(id).orElse(null);
        return showAssembler.toModel(show);
    }

    @PostMapping("/admin/show")
    public ResponseEntity<?> newShow(@RequestBody Show newShow) {
        Show saveNewShow = showRepository.save(newShow);
        return ResponseEntity.created(linkTo(methodOn(ShowApiController.class)
                        .aShow(saveNewShow.getId())) // Pass the ID of the saved artist
                        .toUri())  // Convert the link into a URI format
                .body(saveNewShow); // Include the saved artist object in the response body
    }

    @PutMapping("/admin/show/{id}")
    public ResponseEntity<?> updateShow(@RequestBody Show updatedShow, @PathVariable long id) {
        Show updateTheShow = showRepository.findById(id)
                .map(show -> { // If the artist exists, update their information
                    show.setSlug(updatedShow.getSlug());
                    show.setTitle(updatedShow.getTitle());
                    show.setPosterUrl(updatedShow.getPosterUrl());
                    show.setDuration(updatedShow.getDuration());
                    show.setCreated_in(updatedShow.getCreated_in());
                    show.setIsBookable(updatedShow.getIsBookable());
                    return showRepository.save(show);
                }).orElseGet(() -> { // If the artist doesn't exist, create a new one
                    updatedShow.setId(id); // Set the ID for the new artist
                    return showRepository.save(updatedShow);
                });

        return ResponseEntity.created( // Indicates that the resource was created successfully
                        linkTo(methodOn(ShowApiController.class) // Creates a link to the "anArtist" method for the updated artist
                                .aShow(updateTheShow.getId())) // Pass the ID of the updated artis
                                .toUri()) // Convert the link into a URI format
                .body(showAssembler.toModel(updateTheShow)); // Return the updated artist as a HATEOAS model
    }

    @DeleteMapping("/admin/show/{id}")
    public ResponseEntity<?> deleteShow(@PathVariable long id) {
        showRepository.deleteById(id);
        // Return a ResponseEntity with a "no content" status (204), indicating the deletion was successful
        return ResponseEntity.noContent().build();
    }


}
