package be.icc.Pid_Reservations_2024.api.Controllers;


import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Repositories.ShowRepository;
import be.icc.Pid_Reservations_2024.api.Hateoas.showModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This is the API controller for managing shows.
 * It handles HTTP requests related to shows, such as retrieving, creating, updating, and deleting shows.
 */
@RestController
@RequestMapping("/api")
public class ShowApiController {

    private final ShowRepository showRepository;
    private final showModelAssembler showAssembler;

    /**
     * Constructor to initialize the controller with the repository and assembler.
     *
     * @param showRepository The repository for accessing show data.
     * @param showAssembler  The assembler to convert shows into HATEOAS models.
     */
    public ShowApiController(ShowRepository showRepository, showModelAssembler showAssembler) {
        this.showRepository = showRepository;
        this.showAssembler = showAssembler;
    }

    /**
     * Handles the GET request to retrieve all shows.
     *
     * @return A collection model of all shows with HATEOAS links.
     */
    @GetMapping("/shows")
    public CollectionModel<EntityModel<Show>> allShows() {
        // Fetch all shows from the database and convert each show to a HATEOAS model
        List<EntityModel<Show>> shows = showRepository.findAll() // Get all shows from DB
                .stream() // Convert the list into a stream to apply functional operations
                .map(showAssembler::toModel) // Convert each Show to an EntityModel with HATEOAS links
                .toList(); // Collect the results into a list

        // Return a CollectionModel with the list of shows and a link to the "/shows" endpoint
        return CollectionModel.of(shows,
                linkTo(methodOn(ShowApiController.class).allShows()) // Link to the allShows() method
                        .withRel("All shows"));  // Add a link to navigate to all shows)
    }

    /**
     * Handles the GET request to retrieve a specific show by ID.
     *
     * @param id The ID of the show to retrieve.
     * @return The show with the corresponding ID as an EntityModel with HATEOAS links.
     */
    @GetMapping("/show/{id}")
    public EntityModel<Show> aShow(@PathVariable long id) {
        // Retrieve the show by ID and return it as a HATEOAS model
        Show show = showRepository.findById(id).orElse(null);
        return showAssembler.toModel(show);
    }

    /**
     * Handles the POST request to create a new show.
     *
     * @param newShow The show details to create.
     * @return A ResponseEntity with the URI of the created show and the show object.
     */
    @PostMapping("/admin/show")
    public ResponseEntity<?> newShow(@RequestBody Show newShow) {
        // Save the new show to the database
        Show saveNewShow = showRepository.save(newShow);

        // Return a response indicating successful creation with a link to the new show
        return ResponseEntity.created(linkTo(methodOn(ShowApiController.class)
                        .aShow(saveNewShow.getId())) // Link to the created show
                        .toUri())  // Convert the link into a URI format
                .body(saveNewShow); // Return the created show object in the response body
    }

    /**
     * Handles the PUT request to update an existing show.
     *
     * @param updatedShow The updated show details.
     * @param id          The ID of the show to update.
     * @return A ResponseEntity with the URI of the updated show and the updated show object.
     */
    @PutMapping("/admin/show/{id}")
    public ResponseEntity<?> updateShow(@RequestBody Show updatedShow, @PathVariable long id) {
        // Update the show if it exists, otherwise create a new show with the provided ID
        Show updateTheShow = showRepository.findById(id)
                .map(show -> { // If the show exists, update its details
                    show.setSlug(updatedShow.getSlug());
                    show.setTitle(updatedShow.getTitle());
                    show.setPosterUrl(updatedShow.getPosterUrl());
                    show.setDuration(updatedShow.getDuration());
                    show.setCreated_in(updatedShow.getCreated_in());
                    show.setIsBookable(updatedShow.getIsBookable());
                    return showRepository.save(show);
                }).orElseGet(() -> { // If the show doesn't exist, create a new one
                    updatedShow.setId(id); // Set the ID for the new show
                    return showRepository.save(updatedShow);
                });

        // Return a response with a link to the updated show and the updated show object
        return ResponseEntity.created(
                        linkTo(methodOn(ShowApiController.class)
                                .aShow(updateTheShow.getId())) // Link to the updated show
                                .toUri()) // Convert the link to a URI
                .body(showAssembler.toModel(updateTheShow)); // Return the updated show as a HATEOAS model
    }

    /**
     * Handles the DELETE request to delete a show by its ID.
     *
     * @param id The ID of the show to delete.
     * @return A ResponseEntity indicating the successful deletion.
     */
    @DeleteMapping("/admin/show/{id}")
    public ResponseEntity<?> deleteShow(@PathVariable long id) {
        // Delete the show from the database by its ID
        showRepository.deleteById(id);
        // Return a response indicating the deletion was successful (no content)
        return ResponseEntity.noContent().build();
    }


}
