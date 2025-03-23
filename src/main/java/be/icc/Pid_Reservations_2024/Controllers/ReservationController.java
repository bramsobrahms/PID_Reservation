package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Representation;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.RepresentationsService;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ReservationController {

    @Autowired
    private ShowService showService;
    @Autowired
    private RepresentationsService representationsService;

    @GetMapping("/reservation/{id}")
    public String reservation(@PathVariable("id") long id, Model model) throws JsonProcessingException {
        Representation representation =  representationsService.getRepresentation(id);

        model.addAttribute("representation", representation);
        model.addAttribute("title", representation.getShow().getTitle());

        return "Reservation/reservation";
    }
}
