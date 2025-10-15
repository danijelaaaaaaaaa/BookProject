package com.example.bookproject.controllers;

import com.example.bookproject.models.User;
import com.example.bookproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping({"/", "/index"})
    public String index(Model model, Principal principal) {
        return "index";
    }


}
