package com.example.bookproject.dto;

import com.example.bookproject.models.Author;
import com.example.bookproject.models.Genre;
import com.example.bookproject.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BookDTO {
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max=200, message = "Name cannot be longer than 200 characters")
    private String name;
    @Size(max=2000, message = "Description cannot be longer than 2000 characters")
    private String description;

    private List<Long> genreIds;
    private String imagePath;
    private List<Long> authorIds;
    private User addedBy;


}
