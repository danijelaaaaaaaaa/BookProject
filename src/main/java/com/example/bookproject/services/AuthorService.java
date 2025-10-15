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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ImageStorageService imageStorageService;

    @Transactional
    public void save(AuthorDTO authorDTO, MultipartFile file) throws IOException {
        Author author = new Author();
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setBiography(authorDTO.getBiography());
        List<Book> books = bookRepository.findAllById(authorDTO.getBooksIds());
        author.setBooks(books);
        if(file != null && !file.isEmpty()) {
            String imagePath = imageStorageService.storeImage(file);
            author.setImagePath(imagePath);
        }
        authorRepository.save(author);
        if (!books.isEmpty()) {
            for (Book book : books) {
                book.getAuthor().add(author);
            }
            bookRepository.saveAll(books);
        }

    }

    @Transactional
    public void delete(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author != null) {
            removeAuthorFromAllBooks(author);

            imageStorageService.deleteFile(author.getImagePath());

            authorRepository.delete(author);
        }
    }

    @Transactional
    protected void removeAuthorFromAllBooks(Author author) {
        List<Book> books = bookRepository.findByAuthorContaining(author);
        for (Book book : books) {
            book.getAuthor().remove(author);
        }
        bookRepository.saveAll(books);
    }

    @Transactional
    public void update(Long id, AuthorDTO authorDTO, MultipartFile file) throws IOException {
        Author existing = authorRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setFirstName(authorDTO.getFirstName());
            existing.setLastName(authorDTO.getLastName());
            existing.setBiography(authorDTO.getBiography());
            if(file!= null && !file.isEmpty()) {
                imageStorageService.deleteFile(existing.getImagePath());
                String newPath = imageStorageService.storeImage(file);
                existing.setImagePath(newPath);
            }
            if(authorDTO.getBooksIds() != null && !authorDTO.getBooksIds().isEmpty()) {
                List<Book> books = bookRepository.findByAuthorContaining(existing);
                existing.setBooks(books);
            }

        }
    }

}
