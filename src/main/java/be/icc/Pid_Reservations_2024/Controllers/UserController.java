package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Enums.Roles;
import be.icc.Pid_Reservations_2024.Models.User;
import be.icc.Pid_Reservations_2024.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/user/{id}")
    public String user(@PathVariable("id") long id, Model model) {
        User user = userService.getUser(id);

        model.addAttribute("user", user);
        model.addAttribute("title", "Profile");

        return "User/show";
    }

    @GetMapping("/user/{id}/edit")
    public String editProfile(Model model, @PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        User user = userService.getUser(id);

        model.addAttribute("user", user);

        // Generate the return link for cancel
        String referrer = httpServletRequest.getHeader("Referer");

        if(referrer != null && referrer.equals("")) {
            model.addAttribute("back", referrer);
        }else {
            model.addAttribute("back", "/user/"+user.getId());
        }

        return "User/edit";
    }

    @PutMapping("/user/{id}/edit")
    public String saveEditProfile(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "User/edit";
        }

        User userExisting = userService.getUser(id);

        if(userExisting == null) {
            return "/";
        }

        user.setPassword(userExisting.getPassword());

        userService.updateUser(id, user);

        redirectAttributes.addFlashAttribute("successMessage", "Successfully updated!");

        return "redirect:/user/" + user.getId();
    }
}
