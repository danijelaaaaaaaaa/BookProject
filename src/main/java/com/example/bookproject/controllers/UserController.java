package com.example.bookproject.controllers;

import com.example.bookproject.models.Category;
import com.example.bookproject.models.User;
import com.example.bookproject.repositories.CategoryRepository;
import com.example.bookproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable Long id, Principal principal, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);

        List<Category> publicCategories = categoryRepository.findByOwner_IdAndPublicCategoryTrue(id);
        model.addAttribute("categories", publicCategories);

        boolean isOwner = principal != null && principal.getName().equals(user.getUsername());
        model.addAttribute("isOwner", isOwner);

        boolean isFollowing = false;
        if (principal != null && !isOwner) {
            User currentUser = userRepository.findByUsername(principal.getName()).orElseThrow();
            isFollowing = currentUser.getFollowing().contains(user);
        }
        model.addAttribute("isFollowing", isFollowing);
        model.addAttribute("followers", user.getFollowers());
        model.addAttribute("following", user.getFollowing());

        return "user/profile";
    }
}
