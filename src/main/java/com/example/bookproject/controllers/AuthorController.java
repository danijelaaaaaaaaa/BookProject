package com.example.bookproject.controllers;

import com.example.bookproject.dto.AuthorDTO;
import com.example.bookproject.models.Author;
import com.example.bookproject.repositories.AuthorRepository;
import com.example.bookproject.repositories.BookRepository;
import com.example.bookproject.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookRepository bookRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/saveAuthor")
    public String saveAuthor(@Valid @ModelAttribute("authorDTO") AuthorDTO authorDTO, @RequestParam(value = "file", required = false) MultipartFile file, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("books", bookRepository.findAllByOrderByNameAsc());
            model.addAttribute("errorMessage", "Please correct the errors below.");
            return "author/form";
        }
        try {
            authorService.save(authorDTO, file);
            redirectAttributes.addFlashAttribute("successMessage", "Author saved successfully");
            return "redirect:/author/new";
        } catch (Exception e) {
            result.reject("errorMessage", "Error: " + e.getMessage());
            return "author/form";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new")
    public String showAuthorForm(Model model) {
        model.addAttribute("authorDTO", new AuthorDTO());
        model.addAttribute("books", bookRepository.findAllByOrderByNameAsc());
        return "author/form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/editAuthor/{id}")
    public String updateAuthor(@PathVariable Long id, AuthorDTO authorDTO, @RequestParam("file") MultipartFile file, BindingResult result, Model model, RedirectAttributes redirectAttributes) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("isEdit", true);
            model.addAttribute("errorMessage", "Please correct the errors below.");
            return "author/form";
        }
        try {
            authorService.update(id, authorDTO, file);
            redirectAttributes.addFlashAttribute("successMessage", "Author updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating author: " + e.getMessage());
        }
        return "redirect:/author/details/{id}";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/editAuthor/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());
        authorDTO.setBiography(author.getBiography());
        authorDTO.setImagePath(author.getImagePath());
        model.addAttribute("authorDTO", authorDTO);
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("isEdit", true);
        return "author/form";
    }

    @GetMapping("/list-authors")
    public String listAuthors(Model model) {
        List<Author> authors = authorRepository.findAll();
        for (Author a : authors) {
            System.out.println("Author " + a.getFirstName() + " imagePath=" + a.getImagePath());
        }
        model.addAttribute("authors", authors);
        return "author/list-authors";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            authorService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Author deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting author: " + e.getMessage());
        }
        return "redirect:/author/list-authors";
    }

    @GetMapping("/details/{id}")
    public String showAuthorDetails(@PathVariable Long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow();
        model.addAttribute("author", author);
        return "author/details";
    }

    @GetMapping("/search-author")
    public String searchAuthors(@RequestParam("keyword") String query, Model model) {
        List<Author> results = authorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
        model.addAttribute("authors", results);
        model.addAttribute("query",query);
        return "author/list-authors";
    }
}
