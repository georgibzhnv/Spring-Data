package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.Book;
import com.softuni.springintroex.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate localDate);

    List<Book> getBooksByAuthor_FirstNameOrderByReleaseDateDescTitleAsc(String authorFirstName);
}
