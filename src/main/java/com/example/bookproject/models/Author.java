package com.example.bookproject.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    private String biography;

    @ManyToMany(mappedBy = "author")
    private List<Book> books;

    private String imagePath;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
