package com.example.bookproject.services;

import com.example.bookproject.dto.GenreDTO;
import com.example.bookproject.models.Book;
import com.example.bookproject.models.Genre;
import com.example.bookproject.repositories.GenreRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    BookService bookService;

    @Transactional
    public void save(GenreDTO genreDTO) {
        if (genreRepository.existsByNameIgnoreCase(genreDTO.getName())) {
            throw new IllegalArgumentException("Genre name already exists");
        }
        Genre toSave = GenreDTO.toEntity(genreDTO);
        genreRepository.save(toSave);
    }

    public List<GenreDTO> list() {
        return genreRepository.findAll(Sort.by("name")).stream().map(GenreDTO::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public void deleteByName(String genreName) {
        Genre g = genreRepository.findByNameIgnoreCase(genreName);
        if (g == null) {
            throw new NoSuchElementException("Genre not found");
        }
        bookService.removeGenreFromAllBooks(g);
        genreRepository.delete(g);
    }

    public void updateGenre(String oldName, String newName) {
        if (oldName.equals(newName)) {
            throw new IllegalArgumentException("New name cannot be the same");
        }
        if (genreRepository.existsByNameIgnoreCase(newName)) {
            throw new IllegalArgumentException("Genre name already exists");
        }
        Genre g = genreRepository.findByNameIgnoreCase(oldName);
        if (g == null) {
            throw new NoSuchElementException("Genre not found");
        }
        g.setName(newName);
        genreRepository.save(g);
    }

    public List<GenreDTO> searchGenresByName(String query) {
        return  genreRepository.findByNameContainingIgnoreCase(query).stream().map(genre -> new GenreDTO(genre.getId(),
                genre.getName(), genre.getBooks().size())).collect(Collectors.toList());
    }
}
