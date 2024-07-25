package org.sayem.librarymanagementsystem.controller;

import org.sayem.librarymanagementsystem.utils.LibraryUtils;
import org.sayem.librarymanagementsystem.dto.UserRegistrationRequest;
import org.sayem.librarymanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;


    @GetMapping("/login")
    public String login() {
        return LibraryUtils.LOGIN_PAGE;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationRequest());
        return LibraryUtils.REGISTRATION_PAGE;
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationRequest registrationDto) {
        userService.registerNewUser(registrationDto);
        return "redirect:/verify?registered=true";
    }

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("token") String token, Model model) {
        boolean verified = userService.verifyUser(token);
        if (verified) {
            model.addAttribute("verified", true);
        } else {
            model.addAttribute("verified", false);
        }
        return LibraryUtils.VERIFICATION_PAGE;
    }

}
