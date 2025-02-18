package be.icc.Pid_Reservations_2024.api.Controllers;

import be.icc.Pid_Reservations_2024.Config.SpringSecurityConfig;
import be.icc.Pid_Reservations_2024.Enums.Roles;
import be.icc.Pid_Reservations_2024.Models.User;
import be.icc.Pid_Reservations_2024.Repositories.UserRepository;
import be.icc.Pid_Reservations_2024.api.Hateoas.UserModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class UserApiController {

    private final UserRepository userRepository;
    private final UserModelAssembler userAssembler;
    private final SpringSecurityConfig springSecurityConfig;
    private final PasswordEncoder passwordEncoder;

    public UserApiController(UserRepository userRepository, UserModelAssembler userAssembler, SpringSecurityConfig springSecurityConfig, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.springSecurityConfig = springSecurityConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> allUsers() {
        List<EntityModel<User>> users = userRepository.findAll()
                .stream()
                .map(userAssembler::toModel)
                .toList();

        return CollectionModel.of(users,
                linkTo(methodOn(UserApiController.class).allUsers())
                        .withSelfRel());
    }


    @GetMapping("/user/{id}")
    public EntityModel<User> anUser(@PathVariable long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found")) ;
        return userAssembler.toModel(user);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        User savedUser = userRepository.save(newUser);

        return ResponseEntity.created(linkTo(methodOn(UserApiController.class)
                .anUser(savedUser.getId()))
                .toUri())
                .body(savedUser);
    }

    @PutMapping("/user/{id}/update")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User updateUser) {
        User updateTheUSer = userRepository.findById(id)
                .map(user -> {
                    user.setLastName(updateUser.getLastName());
                    user.setFirstName(updateUser.getFirstName());
                    user.setLogin(updateUser.getLogin());
                    user.setEmail(updateUser.getEmail());
                    user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
                    user.setRole(Roles.MEMBER);
                    user.setLanguage(updateUser.getLanguage());
                    return userRepository.save(user);
                }).orElseGet( () -> {
                    updateUser.setId(id);
                    return userRepository.save(updateUser);
                });

        return ResponseEntity.created(linkTo(methodOn(UserApiController.class)
                .anUser(updateTheUSer.getId()))
                .toUri())
                .body(userAssembler.toModel(updateTheUSer).getContent());
    }

    @PatchMapping("user/{id}/change-password")
    public ResponseEntity<User> changePassword(@PathVariable long id, @RequestBody User changePassword) {
        User changePasswordUser = userRepository.findById(id)
                .map( user -> {
                    user.setPassword(passwordEncoder.encode(changePassword.getPassword()));
                    return userRepository.save(user);
                }).orElseThrow();

        return ResponseEntity.ok(changePasswordUser);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
       userRepository.deleteById(id);
       return ResponseEntity.noContent().build();
    }
}
