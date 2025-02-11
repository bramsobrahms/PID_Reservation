package be.icc.Pid_Reservations_2024.api.Hateoas;

import be.icc.Pid_Reservations_2024.Models.Artist;
import be.icc.Pid_Reservations_2024.api.Controllers.ArtistApiController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ArtistModelAssembler implements RepresentationModelAssembler<Artist, EntityModel<Artist>> {

    /**
     * Converts an `Artist` object into an `EntityModel<Artist>`, adding HATEOAS links.
     * HATEOAS => Hypermedia As The Engine Of Application State
     *
     * @param artist The artist to convert into an EntityModel.
     * @return An `EntityModel<Artist>` with the artist a links for navigation in the API
     */
    @Override
    public EntityModel<Artist> toModel(Artist artist) {

        // Creating an EntityModel for the artist with HATEOAS links
        return EntityModel.of(artist,
                linkTo(methodOn(ArtistApiController.class).anArtist(artist.getId())).withSelfRel(), // Link point to the current artist resource
                linkTo(methodOn(ArtistApiController.class).allArtists()).withRel("artists")); // Link to All artists
    }
}
