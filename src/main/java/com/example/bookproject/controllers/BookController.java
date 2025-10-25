package com.example.bookproject.controllers;

import com.example.bookproject.dto.BookDTO;
import com.example.bookproject.models.*;
import com.example.bookproject.repositories.*;
import com.example.bookproject.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/new")
    public String showBookForm(Model model) {
        model.addAttribute("bookDTO", new BookDTO());
        model.addAttribute("genres", genreRepository.findAllByOrderByNameAsc());
        model.addAttribute("authors", authorRepository.findAllByOrderByFirstNameAsc());
        return "books/form";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("bookDTO") BookDTO bookDTO, @RequestParam("file") MultipartFile file, Principal principal, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("genres", genreRepository.findAll());
            model.addAttribute("authors", authorRepository.findAll());
            model.addAttribute("errorMessage", "Please correct the errors below.");
            return "books/form"; // return to the same page
        }
        try {
            User user = userRepository.findByUsername(principal.getName()).orElseThrow(()->new RuntimeException("Username not found"));
            bookService.save(bookDTO, file, user);
            redirectAttributes.addFlashAttribute("successMessage", "Book saved successfully");
            return "redirect:/books/new";
        } catch (Exception e) {
            result.reject("error", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
            return "books/form";
        }
    }

    @GetMapping("/list-books")
    public String listBooks(Model model) {
        List<Book> books = bookRepository.findAll();

        //potrebno da bi radilo filtriranje
        model.addAttribute("genres", genreRepository.findAllByOrderByNameAsc());
        model.addAttribute("authors", authorRepository.findAllByOrderByFirstNameAsc());

        model.addAttribute("books", books);
        return "books/list-books";
    }

    @GetMapping("/details/{id}")
    public String showBookDetails(@PathVariable Long id, Model model, Principal principal) {
        Book book = bookRepository.findById(id).orElseThrow();
        model.addAttribute("book", book);

        if (principal != null) {
            List<Category> userCategories = categoryRepository.findByOwner_Username(principal.getName());
            userCategories.removeIf(category ->
                    category.getBooks().stream().anyMatch(b -> b.getId().equals(book.getId()))
            );
            model.addAttribute("userCategories", userCategories);
        }
        return "books/details";
    }

    @PostMapping("/editBook/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute("bookDTO") BookDTO bookDTO,Model model, BindingResult result, Principal principal, @RequestParam("file") MultipartFile file, @RequestParam(value = "existingImagePath", required = false) String existingImagePath, RedirectAttributes redirectAttributes) throws IOException {
        Book book = bookRepository.findById(id).orElseThrow();

        if(!hasAccess(principal, book)) {
                return "redirect:/auth/accessDenied";
        }

        if (result.hasErrors()) {
            model.addAttribute("genres", genreRepository.findAll());
            model.addAttribute("authors", authorRepository.findAll());
            model.addAttribute("errorMessage", "Please correct the errors below.");
            return "books/form"; // return to the same page
        }
        try {
            if(bookDTO.getImagePath() == null || bookDTO.getImagePath().isBlank() && existingImagePath != null && !existingImagePath.isBlank() ) {
                bookDTO.setImagePath(existingImagePath);
            }
            bookService.editBook(id, bookDTO, file);
            redirectAttributes.addFlashAttribute("successMessage", "Book edited successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }

        return "redirect:/books/details/{id}";
    }
    @GetMapping("/editBook/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow();
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setDescription(book.getDescription());
        dto.setImagePath(book.getImagePath());
        dto.setAuthorIds(book.getAuthor().stream().map(Author::getId).toList());
        dto.setGenreIds(book.getGenre().stream().map(Genre::getId).toList());
        model.addAttribute("bookDTO", dto);
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("isEdit", true);
        return "books/form";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        Book book = bookRepository.findById(id).orElseThrow();

        if(!hasAccess(principal, book)) {
            return "redirect:/auth/accessDenied";
        }
        try{
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully");
        } catch(Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
        }
        return "redirect:/books/list-books";
    }

    private boolean hasAccess(Principal principal, Book book) {
        if(principal == null) {
            return false;
        }
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isUser = book.getAddedBy() != null && principal.getName().equals(book.getAddedBy().getUsername());
        return isAdmin || isUser;

    }

    @GetMapping("/my-books")
    public String listMyBooks(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/auth/accessDenied";
        }
        List<Book> myBooks = bookRepository.findByAddedBy_Username(principal.getName());
        model.addAttribute("books", myBooks);
        model.addAttribute("myBooksPage", true);
        return "books/list-books";
    }

    @GetMapping("/search-books")
    public String searchBooks(@RequestParam("keyword") String keyword,Model model) {
        List<Book> results = bookRepository.findByNameContainingIgnoreCase(keyword);
        model.addAttribute("books", results);
        model.addAttribute("keyword", keyword);
        model.addAttribute("genres", genreRepository.findAllByOrderByNameAsc());
        model.addAttribute("authors", authorRepository.findAllByOrderByFirstNameAsc());
        return "books/list-books";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) Long genre, @RequestParam(required = false) Long author, Model model) {
        List<Book> filtered;

        if(genre != null && author != null ) {
            filtered = bookRepository.findByGenre_IdAndAuthor_Id(genre, author);
        } else if(genre != null ) {
            filtered = bookRepository.findByGenre_Id(genre);
        } else if(author != null ) {
            filtered = bookRepository.findByAuthor_Id(author);
        } else {
            filtered = bookRepository.findAll();
        }

        model.addAttribute("genres", genreRepository.findAllByOrderByNameAsc());
        model.addAttribute("authors", authorRepository.findAllByOrderByFirstNameAsc());
        model.addAttribute("books", filtered);
        return "books/list-books";
    }

}
