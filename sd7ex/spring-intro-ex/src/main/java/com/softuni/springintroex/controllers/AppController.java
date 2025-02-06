package com.softuni.springintroex.controllers;

import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.services.models.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        this.categoryService.seedCategories();
//        this.authorService.seedAuthors();
//        this.bookService.seedBooks();

        //List<Book> books = this.bookService.getAllBooksAfter2000();

//        this.authorService.findAllAuthorsByCountOfBooks()
//                .forEach(author -> {
//                    System.out.printf("%s %s %d%n",author.getFirstName(),author.getLastName(),author.getBooks().size());
//                });
//         this.authorService.findAllAuthorsByBookBefore1990()
//                 .forEach(author -> {
//                     System.out.printf("%s %s%n",author.getFirstName(),author.getLastName());
//                 });
//        this.bookService.getAllBooksByMarkTwain()
//                .forEach(book -> {
//                    System.out.printf("%s %s %d%n",book.getTitle(),book.getReleaseDate(),book.getCopies());
//                });

//        this.bookService.printAllBooksByAgeRestriction(reader.readLine());
//        this.bookService.printAllBooksByEditionTypeAndCopies();
//        this.bookService.printAllBooksByPriceInBound();
//        this.bookService.printAllBooksByBooksNotInYear(reader.readLine());
//        this.bookService.printAllBooksBeforeDate(reader.readLine());
//        this.authorService.printAllAuthorsByFirstNameEndingWith(reader.readLine());
//        this.bookService.printBooksCountWithTitleLengthBiggerThan(Integer.parseInt(reader.readLine()));
//        this.authorService.printAllAuthorsByBookCopies();
//        BookInfo bookByTitle = this.bookService.findBookByTitle(reader.readLine());
//        System.out.println(bookByTitle.getTitle());
//        System.out.println(bookByTitle.getPrice());
//        System.out.println(bookByTitle.getCopies());

        String date = reader.readLine();
        int copies= Integer.parseInt(reader.readLine());
        this.bookService.printUpdatedCopies(date,copies);
    }
}
