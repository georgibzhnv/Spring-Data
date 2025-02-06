package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.entities.Category;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    @Query("SELECT a FROM Author a order by size(a.books) desc ")
    List<Author> findAuthorByCountOfBooks();

    @Query("SELECT DISTINCT a FROM Author a " +
            "JOIN a.books b " +
            "WHERE b.releaseDate < :date")
    List<Author> findAuthorsWithBooksReleasedBefore1990(@Param("date")LocalDate localDate);
}
