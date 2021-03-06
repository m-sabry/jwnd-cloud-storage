package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("signup")
    public String signupView(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("signup")
    public String signup(@ModelAttribute User user, Model model){

        String signupError = null;

        if (!userService.isUsernameAvailable(user.getUserName())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int createdUser = userService.create(user);
            if (createdUser < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError != null) {
            model.addAttribute("signupError", signupError);
            return "signup";
        }

        model.addAttribute("signupSuccess",true);
        return "login";
    }
}
