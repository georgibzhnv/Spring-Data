package com.softuni.gamestore;

import com.softuni.gamestore.domain.dtos.AddGameDto;
import com.softuni.gamestore.domain.dtos.DeleteGameDto;
import com.softuni.gamestore.domain.dtos.UserLoginDto;
import com.softuni.gamestore.domain.dtos.UserRegisterDto;
import com.softuni.gamestore.domain.entities.Game;
import com.softuni.gamestore.services.GameService;
import com.softuni.gamestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class Runner implements CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public Runner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String[] tokens = reader.readLine().split("\\|");

            switch (tokens[0]) {
                case "RegisterUser":
                    UserRegisterDto registerDto = new UserRegisterDto(tokens[1], tokens[2], tokens[3], tokens[4]);
                    System.out.println(this.userService.registerUser(registerDto));
                    break;
                case "LoginUser":
                    UserLoginDto loginDto = new UserLoginDto(tokens[1], tokens[2]);
                    System.out.println(this.userService.loginUser(loginDto));
                    break;
                case "Logout":
                    System.out.println(this.userService.logout());
                    break;
                case"AddGame":
                    LocalDate date = LocalDate.parse(tokens[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    AddGameDto addGameDto = new AddGameDto(tokens[1],new BigDecimal(tokens[2]),Double.parseDouble(tokens[3]),tokens[4],tokens[5],tokens[6],date);
                    System.out.println(this.gameService.createGame((addGameDto)));
                    break;
                case "DeleteGame":
                    DeleteGameDto deleteGameDto = new DeleteGameDto(Long.parseLong(tokens[1]));
                    System.out.println(this.gameService.deleteGame(deleteGameDto));
                    break;
                case "EditGame":
                    Long gameId = Long.parseLong(tokens[1]);
                    LocalDate editDate = LocalDate.parse(tokens[8], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    AddGameDto editGameDto = new AddGameDto(tokens[2], new BigDecimal(tokens[3]),Double.parseDouble(tokens[4]),tokens[5],tokens[6],tokens[7],editDate);
                    System.out.println(this.gameService.editGame(gameId,editGameDto));
                    break;
                case "AllGames":
                    System.out.println(this.gameService.viewAllGames());
                    break;
                case "DetailsGame":
                    String title = tokens[1];
                    System.out.println(this.gameService.viewGameDetails(title));
                    break;
                case "Exit":
                    return;
            }
        }
    }
}
