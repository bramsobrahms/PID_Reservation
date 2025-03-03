package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Enums.Roles;
import be.icc.Pid_Reservations_2024.Models.User;
import be.icc.Pid_Reservations_2024.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/registration-form")
    public String registration(Model model) {

        if(!model.containsAttribute("user")){
            model.addAttribute("user", new User());
        }

        return "User/registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Failed to register!");
            return "User/registration";
        }

        user.setRole(Roles.MEMBER);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        redirectAttributes.addFlashAttribute("message", "Registration Successful!");

        return "redirect:/" ;
    }
}
