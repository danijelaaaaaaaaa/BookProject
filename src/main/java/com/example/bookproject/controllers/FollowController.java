package com.example.bookproject.controllers;

import com.example.bookproject.models.User;
import com.example.bookproject.repositories.UserRepository;
import com.example.bookproject.services.FollowService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{id}")
    public String followUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow();
        User targetUser = userRepository.findById(id).orElseThrow();

        if (currentUser.equals(targetUser)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You cannot follow yourself!");
            return "redirect:/user/profile/" + id;
        }

        if (!currentUser.getFollowing().contains(targetUser)) {
            currentUser.getFollowing().add(targetUser);
            userRepository.save(currentUser);
            redirectAttributes.addFlashAttribute("successMessage", "You are now following " + targetUser.getUsername());
        }

        return "redirect:/user/profile/" + id;
    }

    @PostMapping("/unfollow/{id}")
    public String unfollowUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername).orElseThrow();
        User targetUser = userRepository.findById(id).orElseThrow();

        if (currentUser.getFollowing().contains(targetUser)) {
            currentUser.getFollowing().remove(targetUser);
            userRepository.save(currentUser);
            redirectAttributes.addFlashAttribute("successMessage", "You unfollowed " + targetUser.getUsername());
        }

        return "redirect:/user/profile/" + id;
    }
}