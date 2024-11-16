package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Types;
import be.icc.Pid_Reservations_2024.Services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
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

    @GetMapping("/type/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Types type = typeService.getType(id);

        model.addAttribute("type", type);
        model.addAttribute("title", "Type Details");

        return "Type/show";
    }

}
