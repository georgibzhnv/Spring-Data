package com.softuni.springintroex.services;

import com.softuni.springintroex.domain.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;
    int getAllAuthorsCount();
    Author findAuthorById(Long id);
    List<Author>findAllAuthorsByCountOfBooks();
    List<Author>findAllAuthorsByBookBefore1990();

    void printAllAuthorsByFirstNameEndingWith(String letter);
    void printAllAuthorsByBookCopies();
}
