package com.example.bookproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageDTO {
    private Long id;

    @NotNull(message = "Receiver is required")
    private Long receiverId;

    @NotBlank(message = "Message content cannot be empty")
    @Size(max = 1000, message = "Message too long")
    private String content;

    private String receiverName;
    private String sentAtFormatted;
}
