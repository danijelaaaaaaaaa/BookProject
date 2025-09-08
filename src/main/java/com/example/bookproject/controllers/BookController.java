package com.example.bookproject.controllers;

import com.example.bookproject.dto.BookDTO;
import com.example.bookproject.models.Book;
import com.example.bookproject.repositories.AuthorRepository;
import com.example.bookproject.repositories.BookRepository;
import com.example.bookproject.repositories.GenreRepository;
import com.example.bookproject.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/new")
    public String showBookForm(Model model) {
        model.addAttribute("bookDTO", new BookDTO());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
        return "books/form";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("bookDTO") BookDTO bookDTO, @RequestParam("file") MultipartFile file, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        try {
            bookService.save(bookDTO, file);
            redirectAttributes.addFlashAttribute("message", "Book saved successfully");
            return "redirect:/books/new";
        } catch (Exception e) {
            result.reject("error", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
            return "books/form";
        }
    }

    @GetMapping("/list-books")
    public String listBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "books/list-books";
    }

    @GetMapping("/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow();
        model.addAttribute("book", book);
        return "books/details";
    }
}
