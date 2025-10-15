package com.example.bookproject.controllers;

import com.example.bookproject.dto.UserRegistrationDTO;
import com.example.bookproject.models.User;
import com.example.bookproject.repositories.UserRepository;
import com.example.bookproject.services.UserRegistrationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRegistrationService  userRegistrationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "auth/accessDenied";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new UserRegistrationDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationDTO userRegistrationDTO, BindingResult results, Model model ){

        if(results.hasErrors()){
            return "auth/register";
        }
        try {
            userRegistrationService.save(userRegistrationDTO);
            model.addAttribute("success", "User registered successfully!");
            return "auth/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }

    }

    @GetMapping("/login-success")
    public String loginSuccess(Principal principal, HttpSession session) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        session.setAttribute("currentUser", user);
        return "redirect:/index";
    }



}
