package com.softuni.jsondemo.init;

import com.softuni.jsondemo.entity.Post;
import com.softuni.jsondemo.entity.Role;
import com.softuni.jsondemo.entity.User;
import com.softuni.jsondemo.service.PostService;
import com.softuni.jsondemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final List<Post>SAMPLE_POSTS=List.of(
            new Post(
                    "Welcome to Spring Data",
                    "Developing data access object with Spring Data is easy ...",
                    "https://www.publicdomainpictures.net/pictures/320000/velka/rosa-klee-blute-blume.jpg",
                    1L
            ),
            new Post(
                    "Reactive Spring Data",
                    "Check R2DBC for reactive JDBC API ...",
                    "https://www.publicdomainpictures.net/pictures/70000/velka/spring-grass-in-sun-light.jpg",
                    1L
            ),
            new Post(
                    "New in Spring 5",
                    "Webflux provides reactive and non-blocking web service implementation ...",
                    "https://www.publicdomainpictures.net/pictures/320000/velka/blute-blumen-garten-bluhen-1577191608U.jpg",
                    1L
            ),
            new Post(
                    "Beginning REST with Spring 5",
                    "Spring MVC and WebFlux make implementing RESTful services really easy ...",
                    "https://www.publicdomainpictures.net/pictures/20000/velka/baby-lamb.jpg",
                    1L
            )
    );
    private static final List<User>SAMPLE_USERS = List.of(
            new User("Default","Admin","admin","admin", Role.ADMIN,
                    "https://www.publicdomainpictures.net/pictures/20000/velka/baby-lamb.jpg"),
            new User("Ivan","Ivanov","ivan","ivan123", Role.USER,
                    "https://www.publicdomainpictures.net/pictures/20000/velka/baby-lamb.jpg")
    );

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        SAMPLE_USERS.forEach(userService::addUser);
        SAMPLE_POSTS.forEach(postService::addPost);
    }
}
