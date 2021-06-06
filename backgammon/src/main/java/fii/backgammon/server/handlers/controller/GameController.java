package fii.backgammon.server.handlers.controller;

import fii.backgammon.server.handlers.dao.GameDAO;
import fii.backgammon.server.handlers.entity.Game;
import fii.backgammon.server.handlers.util.DBConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private List<Game> games = new ArrayList<>();

    public GameController() {
        Connection connection = DBConnection.getConnection();
        GameDAO.setConnection(connection);
        games = GameDAO.getAllGames();
    }

    @GetMapping
    public List<Game> getGames() {
        return games;
    }

    @GetMapping("/{id}")
    public Game getGame(@PathVariable("id") String id) {
        Game game = games.stream().filter(g -> g.getUserId().equals(id)).findFirst().orElse(null);
        return game;
    }

    @PostMapping(value="/create", consumes = "application/json")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        games.add(game);
        GameDAO.insertGame(game);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Game> updateGame(@RequestBody Game game) {
        Game returnGame = games.stream().filter(g -> g.getUserId().equals(game.getUserId())).findFirst().orElse(null);


        returnGame.setGame(game);
        GameDAO.updateGame(game);
        /*StringBuilder response = new StringBuilder()
                .append(returnGame.getBoard())
                .append("+")
                .append(returnGame.getDice())
                .append("+")
                .append(returnGame.getState());
        */
        return new ResponseEntity<>(returnGame,HttpStatus.OK);
    }
}
