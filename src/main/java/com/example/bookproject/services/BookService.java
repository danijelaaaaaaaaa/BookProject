package com.example.bookproject.services;


import com.example.bookproject.dto.BookDTO;
import com.example.bookproject.models.Author;
import com.example.bookproject.models.Book;
import com.example.bookproject.models.Genre;
import com.example.bookproject.repositories.AuthorRepository;
import com.example.bookproject.repositories.BookRepository;
import com.example.bookproject.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public void removeGenreFromAllBooks(Genre genre) {
        List<Book> books = bookRepository.findByGenreContaining(genre);
        for (Book book : books) {
            book.getGenre().remove(genre);
        }
        bookRepository.saveAll(books);
    }

    public void save(BookDTO bookDTO, MultipartFile file) throws IOException {
        Book book = new Book();
        book.setName(bookDTO.getName());

        List<Genre> genres = genreRepository.findAllById(bookDTO.getGenreIds());
        book.setGenre(genres);

        List<Author> authors = authorRepository.findAllById(bookDTO.getAuthorIds());
        book.setAuthor(authors);

        if(!file.isEmpty()) {
            validateFile(file);
            String fileName = storeImage(file);
            book.setImagePath("/uploads/" + fileName);
        }
        bookRepository.save(book);
    }

    private void validateFile(MultipartFile file)  {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File is not an image");
        }
    }

    private String storeImage(MultipartFile fileName) throws IOException {
        Path uploadPath = Path.of(System.getProperty("user.dir"), "uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFileName = fileName.getOriginalFilename();
        String extension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : null;
        String newFileName = UUID.randomUUID() + extension;
        Path filePath = uploadPath.resolve(newFileName);
        Files.copy(fileName.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }
}
