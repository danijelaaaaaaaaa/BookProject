package com.example.bookproject.controllers;

import com.example.bookproject.dto.AuthorDTO;
import com.example.bookproject.models.Author;
import com.example.bookproject.repositories.AuthorRepository;
import com.example.bookproject.repositories.BookRepository;
import com.example.bookproject.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/saveAuthor")
    public String saveAuthor(@Valid @ModelAttribute("authorDTO") AuthorDTO authorDTO, @RequestParam(value = "file", required = false) MultipartFile file, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            authorService.save(authorDTO, file);
            redirectAttributes.addFlashAttribute("message", "Author saved successfully");
            return "redirect:/author/new";
        } catch (Exception e) {
            result.reject("error", "Error: " + e.getMessage());
            return "author/form";
        }
    }

    @GetMapping("/new")
    public String showAuthorForm(Model model) {
        model.addAttribute("authorDTO", new AuthorDTO());
        model.addAttribute("books", bookRepository.findAll());
        return "author/form";
    }

    @PostMapping("/editAuthor")
    public void updateAuthor(AuthorDTO authorDTO) {

    }

    @GetMapping("/listAuthors")
    public String listAuthors(Model model) {
        return null;
    }

    @PostMapping("/deleteAuthor")
    public String deleteAuthor(AuthorDTO authorDTO) {
        return null;
    }

    @GetMapping("/{id}")
    public String showAuthorDetails(@PathVariable Long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow();
        model.addAttribute("author", author);
        return "author/details";
    }

    @GetMapping("/search")
    public String searchAuthors(@RequestParam("query") String query, Model model) {
        return null;
    }
}
