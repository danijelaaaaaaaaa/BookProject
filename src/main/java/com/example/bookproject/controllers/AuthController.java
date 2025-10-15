package com.example.bookproject.controllers;

import com.example.bookproject.dto.UserRegistrationDTO;
import com.example.bookproject.models.Role;
import com.example.bookproject.models.User;
import com.example.bookproject.repositories.RoleRepository;
import com.example.bookproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public String registerUser(@ModelAttribute("user") UserRegistrationDTO userRegistrationDTO, Model model){

        if(userRepository.existsByUsername(userRegistrationDTO.getUsername())){
            model.addAttribute("error", "Username is already taken!");
            return "auth/register";
        }
        if(userRepository.existsByEmail(userRegistrationDTO.getEmail())){
            model.addAttribute("error", "Email is already taken!");
            return "auth/register";
        }
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found!"));
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        model.addAttribute("success", "User registered successfully!");

        return "auth/login";
    }




}
