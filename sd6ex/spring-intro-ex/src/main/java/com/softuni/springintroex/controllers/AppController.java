package com.softuni.springintroex.controllers;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.entities.Book;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.softuni.springintroex.constants.GlobalConstants.*;

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
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();

        //List<Book> books = this.bookService.getAllBooksAfter2000();

//        this.authorService.findAllAuthorsByCountOfBooks()
//                .forEach(author -> {
//                    System.out.printf("%s %s %d%n",author.getFirstName(),author.getLastName(),author.getBooks().size());
//                });
//         this.authorService.findAllAuthorsByBookBefore1990()
//                 .forEach(author -> {
//                     System.out.printf("%s %s%n",author.getFirstName(),author.getLastName());
//                 });
        this.bookService.getAllBooksByMarkTwain()
                .forEach(book -> {
                    System.out.printf("%s %s %d%n",book.getTitle(),book.getReleaseDate(),book.getCopies());
                });
    }
}
