package com.example.bookproject.repositories;

import com.example.bookproject.models.Author;
import com.example.bookproject.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    List<Author> findAllByOrderByFirstNameAsc();
}
