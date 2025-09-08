package com.example.bookproject.dto;

import com.example.bookproject.models.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class GenreDTO {
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max=60, message = "Name cannot be longer than 60 characters")
    private String name;

    private int bookCount;

    public GenreDTO(long id, String name, int bookCount) {
        this(id,name);
        this.bookCount = bookCount;
    }

    public GenreDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDTO() {

    }

    public static GenreDTO toDTO(Genre genre) {
        if (genre == null) {
            return null;
        }
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        int count = genre.getBooks() != null ? genre.getBooks().size() : 0;
        dto.setBookCount(count);
        return dto;
    }

    public static Genre toEntity(GenreDTO dto) {
        if (dto == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setName(dto.getName());
        return genre;
    }
}

