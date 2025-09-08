package com.example.bookproject.controllers;

import com.example.bookproject.dto.GenreDTO;
import com.example.bookproject.services.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/admin/genre")
public class GenreController {

    @Autowired
    GenreService genreService;

    @PostMapping("/saveGenre")
    public String saveGenre(@Valid @ModelAttribute("genreDTO") GenreDTO genreDTO, BindingResult results, Model model, RedirectAttributes redirectAttributes) {
        if (results.hasErrors()) {
            model.addAttribute("items", genreService.list());
            //model.addAttribute("errorMessage", "Validation failed");
            return "admin/genre/list";
        }
        try {
            genreService.save(genreDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Genre " + genreDTO.getName() + " saved successfully!");
            return "redirect:/admin/genre/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("items", genreService.list());
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/genre/list";
        }
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("items", genreService.list());
        if(!model.containsAttribute("genreDTO")) {
            model.addAttribute("genreDTO", new GenreDTO());
            System.out.println("Success: " + model.getAttribute("successMessage"));
            System.out.println("Error: " + model.getAttribute("errorMessage"));
        }
        return "admin/genre/list";
    }

    @PostMapping("/deleteGenre")
    public String deleteGenreByName(@RequestParam String genreName, RedirectAttributes redirectAttributes) {
        try {
            genreService.deleteByName(genreName);
            redirectAttributes.addFlashAttribute("errorMessage", "Genre " + genreName+ " deleted successfully!");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/genre/list";
    }

    @PostMapping("/updateGenre")
    public String updateGenre(@RequestParam String newName, @RequestParam String oldName, RedirectAttributes redirectAttributes) {
        try {
            genreService.updateGenre(oldName, newName);
            redirectAttributes.addFlashAttribute("successMessage", "Genre " + oldName + " updated successfully!");
        } catch (NoSuchElementException  | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/genre/list";
    }

    @GetMapping("/searchGenres")
    public String searchGenre(@RequestParam String query, Model model) {
        List<GenreDTO> results = genreService.searchGenresByName(query);
        model.addAttribute("items", results);
        model.addAttribute("genreDTO", new GenreDTO());
        return "admin/genre/list";
    }

}
