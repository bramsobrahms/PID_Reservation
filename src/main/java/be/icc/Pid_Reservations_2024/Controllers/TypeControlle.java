package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Types;
import be.icc.Pid_Reservations_2024.Services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class TypeControlle {

    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public String index(Model model) {
        List<Types> types = typeService.getAllTypes();

        model.addAttribute("types", types);
        model.addAttribute("title", "List of types");

        return "Type/index";
    }

}
