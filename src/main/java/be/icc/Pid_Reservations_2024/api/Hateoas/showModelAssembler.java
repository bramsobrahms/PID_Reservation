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
public class showModelAssembler implements RepresentationModelAssembler<ShowsDto, EntityModel<ShowsDto>> {

    Show showUpdate = new Show();

    @Override
    public EntityModel<ShowsDto> toModel(ShowsDto showDto) {
        showUpdate.setId(showDto.getId());

        return EntityModel.of(showDto, linkTo(methodOn(ShowApiController.class).showById(showDto.getId())).withSelfRel(),
                linkTo(methodOn(ShowApiController.class).allShows()).withRel("Shows"),
                linkTo(methodOn(ShowApiController.class).updateShow(showDto.getId(), showUpdate)).withRel("Update"));
    }

    public EntityModel<ShowIdDto> toModel(ShowIdDto showIdDto) {
        showUpdate.setId(showIdDto.getId());

        return EntityModel.of(showIdDto, linkTo(methodOn(ShowApiController.class).showById(showIdDto.getId())).withSelfRel(),
                linkTo(methodOn(ShowApiController.class).allShows()).withRel("Shows"),
                linkTo(methodOn(ShowApiController.class).updateShow(showIdDto.getId(), showUpdate)).withRel("Update"));
    }

    public EntityModel<Show> toModel(Show show) {
        return EntityModel.of(show, linkTo(methodOn(ShowApiController.class).updateShow(show.getId(), showUpdate)).withRel("Update"));
    }
}
