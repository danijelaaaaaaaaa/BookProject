package com.example.bookproject.services;


import com.example.bookproject.dto.BookDTO;
import com.example.bookproject.models.*;
import com.example.bookproject.repositories.AuthorRepository;
import com.example.bookproject.repositories.BookRepository;
import com.example.bookproject.repositories.GenreRepository;
import com.example.bookproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ImageStorageService imageStorageService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void removeGenreFromAllBooks(Genre genre) {
        List<Book> books = bookRepository.findByGenreContaining(genre);
        for (Book book : books) {
            book.getGenre().remove(genre);
        }
        bookRepository.saveAll(books);
    }

    public void save(BookDTO bookDTO, MultipartFile file, User user) throws IOException {
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setDescription(bookDTO.getDescription());
        book.setAddedBy(user);

        List<Genre> genres = genreRepository.findAllById(bookDTO.getGenreIds());
        book.setGenre(genres);

        List<Author> authors = authorRepository.findAllById(bookDTO.getAuthorIds());
        book.setAuthor(authors);

        if(!file.isEmpty()) {
            String imagePath = imageStorageService.storeImage(file);
            book.setImagePath(imagePath);
        }
        bookRepository.save(book);
    }

    public void editBook(Long id, BookDTO bookDTO, MultipartFile file) throws IOException {
        Book existingBook = bookRepository.findById(id).orElseThrow();
        existingBook.setName(bookDTO.getName());
        existingBook.setDescription(bookDTO.getDescription());
        existingBook.setImagePath(bookDTO.getImagePath());

        if(bookDTO.getAuthorIds() != null && !bookDTO.getAuthorIds().isEmpty()) {
            existingBook.setAuthor(authorRepository.findAllById(bookDTO.getAuthorIds()));
        }

        if(bookDTO.getGenreIds() != null && !bookDTO.getGenreIds().isEmpty()) {
            existingBook.setGenre(genreRepository.findAllById(bookDTO.getGenreIds()));
        }

        if(file != null && !file.isEmpty()) {
            imageStorageService.deleteFile(bookDTO.getImagePath());
            String newPath = imageStorageService.storeImage(file);
            existingBook.setImagePath(newPath);
        } else if(bookDTO.getImagePath() != null && !bookDTO.getImagePath().isBlank()) {
            existingBook.setImagePath(bookDTO.getImagePath());
        }


        bookRepository.save(existingBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book existingBook = bookRepository.findById(id).orElseThrow();
        existingBook.getAuthor().clear();
        existingBook.getGenre().clear();

        if(existingBook.getAddedBy() != null) {
            User user = userRepository.findByUsername(existingBook.getAddedBy().getUsername()).orElseThrow();
            user.getAddedBooks().remove(existingBook);
        }



        for(Category c : existingBook.getCategories()) {
            c.getBooks().remove(existingBook);
        }

        imageStorageService.deleteFile(existingBook.getImagePath());

        bookRepository.delete(existingBook);
    }


}
