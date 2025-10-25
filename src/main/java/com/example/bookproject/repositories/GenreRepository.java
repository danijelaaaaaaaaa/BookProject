package com.example.bookproject.repositories;


import com.example.bookproject.dto.GenreDTO;
import com.example.bookproject.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByNameIgnoreCase(String name);
    Genre findByNameIgnoreCase(String name);
    List<Genre> findByNameContainingIgnoreCase(String name);
    List<Genre> findAllByOrderByNameAsc();
}
