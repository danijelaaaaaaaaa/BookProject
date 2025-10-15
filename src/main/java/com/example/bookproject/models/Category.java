package com.example.bookproject.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String name;

    private boolean isPublic;

    private boolean isSystem = false;

    private String description;

    @ManyToMany
    @JoinTable(
            name="category_books",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name="book_id")
    )
    private Set<Book> books = new HashSet<>();;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
