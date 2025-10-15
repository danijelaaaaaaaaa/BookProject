package com.example.bookproject.services;

import com.example.bookproject.models.Category;
import com.example.bookproject.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findUserCategories(String username) {
        return categoryRepository.findByOwner_Username(username);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
}

