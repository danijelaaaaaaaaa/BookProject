package com.example.bookproject.repositories;

import com.example.bookproject.models.Book;
import com.example.bookproject.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGenreContaining(Genre genre);
}
