package be.icc.Pid_Reservations_2024.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Home/index";
    }

}
