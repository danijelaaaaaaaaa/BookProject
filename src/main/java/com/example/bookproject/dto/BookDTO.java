package com.example.bookproject.dto;

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

    private List<Long> genreIds;
    private String imagePath;
    private List<Long> authorIds;
}
