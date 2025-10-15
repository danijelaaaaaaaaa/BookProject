package com.example.bookproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    private Long id;
    @Size(max=100)
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Email cannot be empty")
    @Size(max=100)
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Size(max=100)
    private String password;
}
