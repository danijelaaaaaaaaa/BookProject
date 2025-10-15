package com.example.bookproject.controllers;

import com.example.bookproject.models.Book;
import com.example.bookproject.models.Category;
import com.example.bookproject.models.User;
import com.example.bookproject.repositories.BookRepository;
import com.example.bookproject.repositories.CategoryRepository;
import com.example.bookproject.repositories.UserRepository;
import com.example.bookproject.services.BookService;
import com.example.bookproject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/categories")
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/my")
    public String listMyCategories(Model model, Principal principal) {
        String username = principal.getName();
        List<Category> categories = categoryService.findUserCategories(username);
        model.addAttribute("categories", categories);
        return "categories/list-categories";
    }

    @GetMapping("/new")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/form";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Username not found"));
        category.setOwner(user);
        try {
            categoryRepository.save(category);
            redirectAttributes.addFlashAttribute("successMessage", "Category created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Category creation failed!");
        }


        return "redirect:/categories/my";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(id).orElseThrow();
        if (!category.getOwner().getUsername().equals(principal.getName())) {
            redirectAttributes.addFlashAttribute("errorMessage", "You can only delete your own categories.");
            return "redirect:/categories/my";
        }
        try {
            categoryRepository.delete(category);
            redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting category.");
        }
        return "redirect:/categories/my";
    }

    @GetMapping("/manage/{id}")
    public String manageCategory(@PathVariable Long id, Model model, Principal principal) {
        Category category = categoryRepository.findById(id).orElseThrow();
        if (!category.getOwner().getUsername().equals(principal.getName())) {
            return "redirect:/auth/accessDenied";
        }
        model.addAttribute("category", category);
        model.addAttribute("books", category.getBooks());
        return "categories/manage-category";
    }

    @GetMapping("/details/{id}")
    public String showCategoryDetails(@PathVariable Long id, Model model, Principal principal) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.isPublicCategory() && !category.getOwner().getUsername().equals(principal.getName())) {
            return "redirect:/auth/accessDenied";
        }

        boolean canManage = false;
        if (principal != null) {
            boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
                    .getAuthorities()
                    .stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isOwner = category.getOwner() != null &&
                    principal.getName().equals(category.getOwner().getUsername());
            canManage = isAdmin || isOwner;
        }

        model.addAttribute("category", category);
        model.addAttribute("books", category.getBooks());
        model.addAttribute("allBooks", bookRepository.findAll());
        model.addAttribute("canManage", canManage);
        return "categories/details";
    }

    @PostMapping("/addBookToCategory")
    public String addBookToCategory(@RequestParam Long categoryId,
                                    @RequestParam Long bookId,
                                    Principal principal,
                                    RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getOwner().getUsername().equals(principal.getName())) {
            return "redirect:/auth/accessDenied";
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        category.getBooks().add(book);
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("successMessage", "Book added to category!");
        return "redirect:/categories/details/" + categoryId;
    }

    @PostMapping("/removeBook")
    public String removeBookFromCategory(@RequestParam Long bookId,
                                         @RequestParam Long categoryId,
                                         Principal principal,
                                         RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin && !category.getOwner().getUsername().equals(principal.getName())) {
            return "redirect:/auth/accessDenied";
        }

        category.getBooks().remove(book);
        categoryRepository.save(category);

        redirectAttributes.addFlashAttribute("successMessage",
                "Book removed from category '" + category.getName() + "'.");
        return "redirect:/categories/details/" + categoryId;
    }

    @PostMapping("/copy/{id}")
    public String copyCategory(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        Category original = categoryRepository.findById(id).orElseThrow();
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();

        if (original.getOwner().getUsername().equals(user.getUsername())) {
            redirectAttributes.addFlashAttribute("errorMessage", "You cannot copy your own category.");
            return "redirect:/user/profile/" + original.getOwner().getId();
        }

        if (!original.isPublicCategory()) {
            redirectAttributes.addFlashAttribute("errorMessage", "You can only copy public categories.");
            return "redirect:/user/profile/" + original.getOwner().getId();
        }

        Category copy = new Category();
        copy.setName(original.getName() + " (copy)");
        copy.setDescription(original.getDescription());
        copy.setPublicCategory(false);
        copy.setOwner(user);

        copy.getBooks().addAll(original.getBooks());

        categoryRepository.save(copy);

        redirectAttributes.addFlashAttribute("successMessage", "Category copied to your account!");
        return "redirect:/categories/my";
    }




}
