package com.example.bookproject.services;

import com.example.bookproject.dto.AuthorDTO;
import com.example.bookproject.models.Author;
import com.example.bookproject.models.Book;
import com.example.bookproject.repositories.AuthorRepository;
import com.example.bookproject.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void save(AuthorDTO authorDTO, MultipartFile file) throws IOException {
        Author author = new Author();
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setBiography(authorDTO.getBiography());
        List<Book> books = bookRepository.findAllById(authorDTO.getBooksIds());
        author.setBooks(books);
        authorRepository.save(author);
        if (!books.isEmpty()) {
            for (Book book : books) {
                book.getAuthor().add(author);
            }
        }
        bookRepository.saveAll(books);
    }

}
