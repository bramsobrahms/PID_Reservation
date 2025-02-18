package be.icc.Pid_Reservations_2024.api.Hateoas;

import be.icc.Pid_Reservations_2024.Models.User;
import be.icc.Pid_Reservations_2024.api.Controllers.UserApiController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    /**
     * Converts an `User` object into an `EntityModel<User>`, adding HATEOAS links.
     *
     * @param user The User to convert into an EntityModel
     * @return an `EntityModel<User>` with the user a links for navigation in the API
     */
    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user,
                linkTo(methodOn(UserApiController.class).anUser(user.getId())).withSelfRel(),
                linkTo(methodOn(UserApiController.class).allUsers()).withRel("users"));
    }

}
