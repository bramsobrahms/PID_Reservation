package be.icc.Pid_Reservations_2024.api.Hateoas;

import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.api.Controllers.ShowApiController;
import be.icc.Pid_Reservations_2024.api.Dto.ShowIdDto;
import be.icc.Pid_Reservations_2024.api.Dto.ShowsDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class showModelAssembler implements RepresentationModelAssembler<Show, EntityModel<Show>> {
    /**
     * Converts an `Show` object into an `EntityModel<Artist>`, adding HATEOAS links.
     * HATEOAS => Hypermedia As The Engine Of Application State
     *
     * @param show The artist to convert into an EntityModel.
     * @return An `EntityModel<Artist>` with the artist a links for navigation in the API
     */
    @Override
    public EntityModel<Show> toModel(Show show) {
        return EntityModel.of(show,
                linkTo(methodOn(ShowApiController.class).allShows()).withRel("All shows"),
                linkTo(methodOn(ShowApiController.class).aShow(show.getId())).withSelfRel(),
                linkTo(methodOn(ShowApiController.class).newShow(show)).withRel("Create new Show"),
                linkTo(methodOn(ShowApiController.class).updateShow(show, show.getId())).withRel("Update show"),
                linkTo(methodOn(ShowApiController.class).deleteShow(show.getId())).withRel("Delete")
        );

    }
}
