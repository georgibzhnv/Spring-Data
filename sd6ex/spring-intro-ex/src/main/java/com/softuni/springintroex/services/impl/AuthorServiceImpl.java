package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.entities.Category;
import com.softuni.springintroex.repositories.AuthorRepository;
import com.softuni.springintroex.repositories.CategoryRepository;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

}
