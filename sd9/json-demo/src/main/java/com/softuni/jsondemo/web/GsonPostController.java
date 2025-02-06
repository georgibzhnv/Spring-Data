package com.softuni.jsondemo.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softuni.jsondemo.entity.Post;
import com.softuni.jsondemo.service.PostService;
import com.softuni.jsondemo.typeadapter.LocalDateTimeAdapter;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/gson/posts")
@Slf4j
public class GsonPostController {

    @Autowired
    private PostService postService;

    private Gson gson= new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    @GetMapping(produces = "aplication/json")
    public String getPosts(){
        return gson.toJson(postService.getAllPosts());
    }

    @GetMapping(path = "/{id}",produces = "application/json")
    public String getPosts(@PathVariable(name = "id")Long id){
        return gson.toJson(postService.getPostById(id));
    }


    @PostMapping
    public ResponseEntity<String> addPost(@RequestBody String body){
        log.info("Body received: {}",body);
        Post post = gson.fromJson(body,Post.class);
        log.info("Post: {}",post);
        Post created = postService.addPost(post);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}")
                        .buildAndExpand(created.getId().toString())
                .toUri()
        ).body(gson.toJson(created));
    }
}
