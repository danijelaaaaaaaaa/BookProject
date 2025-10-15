package com.example.bookproject.repositories;

import com.example.bookproject.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByOwner_Username(String username);

    List<Category> findByOwner_IdAndPublicCategoryTrue(Long id);
}
