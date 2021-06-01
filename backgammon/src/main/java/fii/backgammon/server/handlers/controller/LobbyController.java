package fii.backgammon.server.handlers.controller;

import fii.backgammon.server.handlers.dao.LobbyDAO;
import fii.backgammon.server.handlers.entity.Lobby;
import fii.backgammon.server.handlers.entity.User;
import fii.backgammon.server.handlers.util.DBConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lobby")
public class LobbyController {

    private List<Lobby> lobbies = new ArrayList<>();

    public LobbyController() {
        Connection connection = DBConnection.getConnection();
        LobbyDAO.setConnection(connection);
        lobbies = LobbyDAO.getAllLobbies();
    }

    @GetMapping
    public List<Lobby> getLobbies() {
        return lobbies;
    }

    @PostMapping(value="/username", consumes = "application/json")
    public ResponseEntity<String> createLobby(@RequestBody User user) {
        Lobby lobby = new Lobby(user);
        lobbies.add(lobby);
        LobbyDAO.insertLobby(lobby);
        LobbyDAO.commit();
        return new ResponseEntity<>("Lobby created", HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> joinLobby(@RequestBody User user2,@PathVariable String username) {
        Lobby lobby = lobbies.stream().filter(l -> l.getUser1().getUsername().equals(username)).findFirst().orElse(null);
        if(lobby == null) {
            return new ResponseEntity<>("Lobby not found",HttpStatus.NOT_FOUND);
        }

        lobby.setUser2(user2);
        LobbyDAO.addUser(lobby,user2);
        return new ResponseEntity<>("User joined a lobby",HttpStatus.OK);
    }
}
