package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.domain.entities.Author;
import com.softuni.springintroex.domain.entities.Book;
import com.softuni.springintroex.domain.repositories.AuthorRepository;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl( AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count()!=0){
            return;
        }
        String [] fileContent = this.fileUtil
                .readFileContent(GlobalConstants.AUTHOR_FILE_PATH);

        Arrays.stream(fileContent)
                .forEach(r->{
                    String[] params = r.split("\\s+");
                    Author author = new Author(params[0],params[1]);

                    this.authorRepository.saveAndFlush(author);
                });


    }

    @Override
    public int getAllAuthorsCount() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(Long id) {
        return this.authorRepository.getOne(id);
    }

    @Override
    public List<Author> findAllAuthorsByCountOfBooks() {
        return this.authorRepository.findAuthorByCountOfBooks();
    }

    @Override
    public List<Author> findAllAuthorsByBookBefore1990() {
        LocalDate releaseDate = LocalDate.of(1990,1,1);
        return this.authorRepository.findAuthorsWithBooksReleasedBefore1990(releaseDate);
    }

    @Override
    public void printAllAuthorsByFirstNameEndingWith(String letter) {
        this.authorRepository.findAllByFirstNameEndsWith(letter)
                .forEach(a-> System.out.printf("%s %s%n",a.getFirstName(),a.getLastName()));
    }

    @Override
    public void printAllAuthorsByBookCopies() {
        List<Author>authors = this.authorRepository.findAll();
        Map<String,Integer> authorCopies = new HashMap<>();
        authors.forEach(author -> {
          int copies=author
                  .getBooks()
                  .stream()
                  .mapToInt(Book::getCopies).sum();
          authorCopies.put(author.getFirstName()+" " + author.getLastName(),copies);
        });
        authorCopies
                .entrySet()
                .stream()
                .sorted((current,next) -> Integer.compare(next.getValue(),current.getValue()))
                .forEach(author-> System.out.printf("%s %d%n",author.getKey(),author.getValue()));

    }

}
