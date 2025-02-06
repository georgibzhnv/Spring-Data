package com.softuni.gamestore.services;

import com.softuni.gamestore.domain.dtos.AddGameDto;
import com.softuni.gamestore.domain.dtos.DeleteGameDto;
import com.softuni.gamestore.domain.dtos.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface GameService {

    String createGame(AddGameDto addGameDto);

    void setLoggedUser(UserDto userDto);
    String deleteGame(DeleteGameDto deleteGameDto);
    String editGame(Long id,AddGameDto editGameDto);
    String viewAllGames();
    String viewGameDetails(String title);
    String viewOwnedGames(UserDto userDto);
}
