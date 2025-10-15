package com.example.bookproject.repositories;

import com.example.bookproject.models.Author;
import com.example.bookproject.models.Book;
import com.example.bookproject.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGenreContaining(Genre genre);

    List<Book> findByAuthorContaining(Author author);

    List<Book> findByAddedBy_Username(String username);

    List<Book> findByNameContainingIgnoreCase(String keyword);

    List<Book> findAllByOrderByNameAsc();

    List<Book> findByGenre_IdAndAuthor_Id(Long genreId, Long authorId);

    List<Book> findByGenre_Id(Long genreId);
    List<Book> findByAuthor_Id(Long authorId);
}
