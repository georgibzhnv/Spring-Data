package com.softuni.springintroex.domain.repositories;

import com.softuni.springintroex.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    @Query("SELECT a FROM Author a order by size(a.books) desc ")
    List<Author> findAuthorByCountOfBooks();

    @Query("SELECT DISTINCT a FROM Author a " +
            "JOIN a.books b " +
            "WHERE b.releaseDate < :date")
    List<Author> findAuthorsWithBooksReleasedBefore1990(@Param("date")LocalDate localDate);

    Set<Author> findAllByFirstNameEndsWith(String letter);
}
