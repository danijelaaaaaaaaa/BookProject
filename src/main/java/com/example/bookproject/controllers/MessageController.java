package com.example.bookproject.controllers;

import com.example.bookproject.dto.MessageDTO;
import com.example.bookproject.models.Message;
import com.example.bookproject.models.User;
import com.example.bookproject.repositories.UserRepository;
import com.example.bookproject.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String inbox(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Message> latestMessages = messageService.getInboxOverview(currentUser.getId());
        model.addAttribute("latestMessages", latestMessages);
        model.addAttribute("currentUser", currentUser);
        return "messages/inbox";
    }

    @GetMapping("/conversation/{userId}")
    public String conversation(@PathVariable Long userId,
                               @RequestParam(defaultValue = "0") int offset,
                               Model model, Principal principal) {
        User me = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        User other = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        messageService.markAsRead(other, me);

        List<Message> messages = messageService.getRecentConversation(me, other, offset + 10);
        model.addAttribute("messages", messages);
        model.addAttribute("receiver", other);
        model.addAttribute("offset", offset);
        return "messages/conversation";
    }

    @PostMapping("/send/{receiverId}")
    public String send(@PathVariable Long receiverId,
                       @RequestParam String content,
                       Principal principal) {

        User sender = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        messageService.send(sender, receiver, content);
        return "redirect:/messages/conversation/" + receiverId;
    }


}