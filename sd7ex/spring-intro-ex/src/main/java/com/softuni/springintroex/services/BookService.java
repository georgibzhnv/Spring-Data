package com.softuni.springintroex.services;

import com.softuni.springintroex.domain.entities.Book;
import com.softuni.springintroex.services.models.BookInfo;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> getAllBooksAfter2000();
    List<Book>getAllBooksByMarkTwain();
    void printAllBooksByAgeRestriction(String ageRes);

    void printAllBooksByEditionTypeAndCopies();

    void printAllBooksByPriceInBound();

    void printAllBooksByBooksNotInYear(String year);

    void printAllBooksBeforeDate(String date);

    void printAllBooksByAuthorsLastNameStarting(String lastName);

    void printBooksCountWithTitleLengthBiggerThan(int length);

    BookInfo findBookByTitle(String title);

    void printUpdatedCopies(String date,int copies);
}
