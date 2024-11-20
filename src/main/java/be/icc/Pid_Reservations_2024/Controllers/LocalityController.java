package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Localities;
import be.icc.Pid_Reservations_2024.Services.LocalitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LocalityController {

    @Autowired
    LocalitiesService localitiesService;

    @GetMapping("/localities")
    public String index(Model model) {
        List<Localities> localities = localitiesService.getAll();
        model.addAttribute("localities", localities);
        model.addAttribute("title", "list of localities");
        return "Locality/index";
    }
}
