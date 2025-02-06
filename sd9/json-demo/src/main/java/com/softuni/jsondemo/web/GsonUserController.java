package com.softuni.jsondemo.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softuni.jsondemo.entity.User;
import com.softuni.jsondemo.service.UserService;
import com.softuni.jsondemo.typeadapter.LocalDateTimeAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/gson/users")
@Slf4j
public class GsonUserController {

    @Autowired
    private UserService userService;

    private Gson gson= new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

    @GetMapping(produces = "aplication/json")
    public String getUsers(){
        return gson.toJson(userService.getAllUsers());
    }

    @GetMapping(path = "/{id}",produces = "application/json")
    public String getUsers(@PathVariable(name = "id")Long id){
        return gson.toJson(userService.getUserById(id));
    }


    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody String body){
        log.info("Body received: {}",body);
        User user = gson.fromJson(body,User.class);
        log.info("User: {}",user);
        User created = userService.addUser(user);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}")
                        .buildAndExpand(created.getId().toString())
                .toUri()
        ).body(gson.toJson(created));
    }
}
