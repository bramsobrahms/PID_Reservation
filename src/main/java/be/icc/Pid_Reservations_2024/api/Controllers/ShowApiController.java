package be.icc.Pid_Reservations_2024.api.Controllers;

import be.icc.Pid_Reservations_2024.Config.SpringSecurityConfig;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Repositories.ShowRepository;
import be.icc.Pid_Reservations_2024.api.Dto.ShowIdDto;
import be.icc.Pid_Reservations_2024.api.Dto.ShowsDto;
import be.icc.Pid_Reservations_2024.api.Hateoas.showModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShowApiController {

    private final ShowRepository showRepository;
    private final showModelAssembler showAssembler;
    private final SpringSecurityConfig springSecurityConfig;

    public ShowApiController(ShowRepository showRepository, showModelAssembler showAssembler, SpringSecurityConfig springSecurityConfig) {
        this.showRepository = showRepository;
        this.showAssembler = showAssembler;
        this.springSecurityConfig = springSecurityConfig;
    }

    @GetMapping("/shows")
    public CollectionModel<EntityModel<ShowsDto>> allShows() {
        List<EntityModel<ShowsDto>> shows = showRepository.findAll()
                .stream()
                .map(this::showsToDto)
                .map(showAssembler::toModel)
                .toList();

        return CollectionModel.of(shows);
    }

    private ShowsDto showsToDto(Show show) {
        ShowsDto showsDto = new ShowsDto();
        showsDto.setId(show.getId());
        showsDto.setTitle(show.getTitle());
        showsDto.setPosterUrl(show.getPosterUrl());
        return showsDto;
    }

    @GetMapping("/show/{id}")
    public EntityModel<ShowIdDto> showById(@PathVariable Long id) {
        Show showId = showRepository.findById(id).orElse(null);

        ShowIdDto showIdDto = showIdToDto(showId);

        return showAssembler.toModel(showIdDto);
    }

    private ShowIdDto showIdToDto(Show show) {
        ShowIdDto dto = new ShowIdDto();
        dto.setId(show.getId());
        dto.setTitle(show.getTitle());
        dto.setPosterUrl(show.getPosterUrl());
        dto.setDuration(show.getDuration());
        dto.setCreated_in(show.getCreated_in());
        dto.setIsBookable(show.getIsBookable());
        return dto;
    }

    @PutMapping("/show/edit/{id}")
    public EntityModel<Show> updateShow(@PathVariable Long id, @RequestBody Show showUpdate) {
        Show show = showRepository.findById(id).orElse(null);

        if (show != null) {
            show.setTitle(showUpdate.getTitle());
            show.setPosterUrl(showUpdate.getPosterUrl());
            show.setDuration(showUpdate.getDuration());
            show.setCreated_in(showUpdate.getCreated_in());
            show.setIsBookable(showUpdate.getIsBookable());
            showRepository.save(show);
        }


        return showAssembler.toModel(show);
    }


}
