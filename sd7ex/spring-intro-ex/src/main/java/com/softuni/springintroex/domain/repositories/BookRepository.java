package com.softuni.springintroex.domain.repositories;

import com.softuni.springintroex.domain.entities.AgeRestriction;
import com.softuni.springintroex.domain.entities.Author;
import com.softuni.springintroex.domain.entities.Book;
import com.softuni.springintroex.domain.entities.EdititonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate localDate);

    List<Book> getBooksByAuthor_FirstNameOrderByReleaseDateDescTitleAsc(String authorFirstName);

    Set<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Set<Book> findAllByEdititonTypeAndCopiesLessThan(EdititonType edititonType, int copiesIsLessThan);

    Set<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerBound, BigDecimal upperBound);

    @Query("SELECT b FROM Book b WHERE FUNCTION('YEAR', b.releaseDate) <> :year")
    Set<Book> findAllByReleaseDateNotInYear(@Param("year") String year);

    Set<Book> findAllByReleaseDateIsLessThan(LocalDate releaseDate);

    Set<Book> findAllByAuthor_LastNameStartsWith(String authorLastName);

    @Query("select count(b) from Book b where length(b.title)> :length")
    int getNumberOfBooksWithTitleLength(@Param("length") int length);

    Book findByTitle(String title);


    @Query("update Book b SET b.copies=b.copies+:copies where b.releaseDate>:date")
    @Modifying
    int updateCopies(@Param("copies") int copies,@Param("date")LocalDate date);

}
