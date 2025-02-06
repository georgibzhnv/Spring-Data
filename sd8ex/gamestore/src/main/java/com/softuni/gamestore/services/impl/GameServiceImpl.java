package com.softuni.gamestore.services.impl;

import com.softuni.gamestore.domain.dtos.AddGameDto;
import com.softuni.gamestore.domain.dtos.DeleteGameDto;
import com.softuni.gamestore.domain.dtos.GameViewDto;
import com.softuni.gamestore.domain.dtos.UserDto;
import com.softuni.gamestore.domain.entities.Game;
import com.softuni.gamestore.domain.entities.Role;
import com.softuni.gamestore.domain.entities.User;
import com.softuni.gamestore.repositories.GameRepository;
import com.softuni.gamestore.repositories.UserRepository;
import com.softuni.gamestore.services.GameService;
import com.softuni.gamestore.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private UserDto userDto;
    private final UserRepository userRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public String createGame(AddGameDto addGameDto) {
        StringBuilder sb = new StringBuilder();
        if (this.userDto == null || this.userDto.getRole().equals(Role.USER)){
            sb.append("Invalid logged in user.");
        } else if (this.validatorUtil.isValid(addGameDto)){
            Game game= this.modelMapper.map(addGameDto,Game.class);
            this.gameRepository.saveAndFlush(game);

            sb.append(String.format("Added %s.",game.getTitle()));
        }else {
            this.validatorUtil.violations(addGameDto)
                    .forEach(e->sb.append(System.lineSeparator()));
        }
        return sb.toString();
    }

    @Override
    public void setLoggedUser(UserDto userDto) {
        this.userDto = userDto;
    }

    @Override
    public String deleteGame(DeleteGameDto deleteGameDto) {
        StringBuilder sb = new StringBuilder();
        if (this.userDto == null || this.userDto.getRole().equals(Role.USER)){
            sb.append("Invalid logged in user.");
        }else {
            Optional<Game> game=this.gameRepository.findById(deleteGameDto.getId());
            if (game.isPresent()){
                this.gameRepository.delete(game.get());
                sb.append(String.format("Game %s was delete.",game.get().getTitle()));
            }else {
                sb.append("Cannot find game.");
            }
        }
        return sb.toString();
    }

    @Override
    public String editGame(Long id, AddGameDto editGameDto) {
        StringBuilder sb = new StringBuilder();
        if (this.userDto == null || this.userDto.getRole().equals(Role.USER)){
            return "Only admins can edit games.";
        }
        Optional<Game>optionalGame = this.gameRepository.findById(id);
        if (optionalGame.isEmpty()){
            return "Game not found.";
        }
        Game game =optionalGame.get();
        if (validatorUtil.isValid(editGameDto)){
            game.setTitle(editGameDto.getTitle());
            game.setPrice(editGameDto.getPrice());
            game.setDescription(editGameDto.getDescription());
            game.setReleaseDate(editGameDto.getReleaseDate());
            game.setSize(editGameDto.getSize());
            game.setImageThumbnail(editGameDto.getImageThumbnail());
            game.setTrailer(editGameDto.getTrailer());

            this.gameRepository.save(game);
            sb.append(String.format("Game %s was edited successfully.", game.getTitle()));
        }else {
            validatorUtil.violations(editGameDto)
                    .forEach(violation->sb.append(violation.getMessage())
                            .append(System.lineSeparator()));
        }
        return sb.toString().trim();
    }

    @Override
    public String viewAllGames() {
        StringBuilder sb = new StringBuilder();
        List<Game>games = this.gameRepository.findAll();
        if (games.isEmpty()){
            sb.append("No games available.");
        }else {
            games.forEach(game -> sb.append(String.format("%s %.2f%n",game.getTitle(),game.getPrice())));
        }
        return sb.toString().trim();
    }

    @Override
    public String viewGameDetails(String title) {
        Optional<Game>game = this.gameRepository.findByTitle(title);
        if (game.isPresent()){
            Game g = game.get();
            return String.format("Title: %s%nPrice: %.2f%nDescription: %s%nRelease Date: %s",
                    g.getTitle(),g.getPrice(),g.getDescription(),g.getReleaseDate());
        }
        return "Game not found.";
    }

    @Override
    public String viewOwnedGames(UserDto userDto) {
        if (userDto == null) {
            return "No user is logged in";
        }
        Optional<User> user = this.userRepository.findById(userDto.getId());
        if (user.isPresent() && !user.get().getGames()) {
            StringBuilder sb = new StringBuilder();
            user.get().getGames()
                    .forEach(game -> sb.append(game.getTitle()).append(System.lineSeparator()));
            return sb.toString().trim();
        }
        return "No games owned.";
    }

}
