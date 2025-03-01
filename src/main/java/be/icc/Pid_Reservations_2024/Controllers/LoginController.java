package be.icc.Pid_Reservations_2024.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(required = false) final Boolean loginRequired, @RequestParam(required = false) final Boolean loginError, @RequestParam(required = false) final Boolean logoutSucces, final Model model) {

        if (loginRequired == Boolean.TRUE) {
            model.addAttribute("errorMessage", "You must log in to access.");
        }

        if (loginError == Boolean.TRUE) {
            model.addAttribute("errorMessage", "Failed to log in.");
        }

        if(logoutSucces == Boolean.TRUE) {
            model.addAttribute("errorMessage", "You have successfully logged out.");
        }
        return "Authentification/login";
    }
}
