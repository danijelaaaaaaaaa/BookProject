package com.example.bookproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AuthorDTO {
    private Long id;
    @NotBlank(message = "Name of the author cannot be empty")
    @Size(max=200, message = "Name of the author cannot be longer than 200 characters")
    private String firstName;
    @Size(max=300)
    private String lastName;
    @Size(max=2000)
    private String biography;
    private String imagePath;
    private List<Long> booksIds;
}
