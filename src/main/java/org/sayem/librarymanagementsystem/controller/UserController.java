package org.sayem.librarymanagementsystem.controller;


import org.sayem.librarymanagementsystem.dto.UserAddRequest;
import org.sayem.librarymanagementsystem.dto.UserRegistrationRequest;
import org.sayem.librarymanagementsystem.entity.User;
import org.sayem.librarymanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/admin/users")
    public String users(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("username", username);
        return "user/users";
    }

    @GetMapping("/admin/users/add")
    public String addUser(Model model) {
        return "user/add-user";
    }

    @PostMapping("/admin/users/add")
    public String saveUser(@ModelAttribute("user") UserAddRequest request) {
        userService.addNewUser(request);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/deactivate/{id}")
    public String deactivateUser(@PathVariable long id) {
        userService.deactivateUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/activate/{id}")
    public String activateUser(@PathVariable long id) {
        userService.activateUser(id);
        return "redirect:/admin/users";
    }
}
