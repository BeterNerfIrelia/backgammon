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

    @GetMapping("/{id}")
    public Lobby getLobby(@PathVariable("id") String id) {
        Lobby lobby = lobbies.stream().filter(l -> l.getUser1().getId().equals(id)).findFirst().orElse(null);
        return lobby;
    }

    @PostMapping(value="/create", consumes = "application/json")
    public ResponseEntity<String> createLobby(@RequestBody User user) {
        Lobby lobby = new Lobby(user);
        lobbies.add(lobby);
        LobbyDAO.insertLobby(lobby);
        LobbyDAO.commit();
        StringBuilder response = new StringBuilder("[").
                                     append(lobby.getUser1().getId()).
                                     append(",").
                                     append(lobby.getUser1().getUsername()).
                                     append(",").
                                     append(lobby.getCode()).
                                     append("]");
        return new ResponseEntity<>(response.toString(), HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> joinLobby(@RequestBody User user2,@PathVariable String username) {
        Lobby lobby = lobbies.stream().filter(l -> l.getUser1().getUsername().equals(username)).findFirst().orElse(null);
        if(lobby == null) {
            return new ResponseEntity<>("404",HttpStatus.NOT_FOUND);
        }

        lobby.setUser2(user2);
        LobbyDAO.addUser(lobby,user2);
        StringBuilder response = new StringBuilder("[")
                .append(lobby.getUser1().getId())
                .append(",")
                .append(lobby.getUser1().getUsername())
                .append(",")
                .append(lobby.getUser2().getId())
                .append(",")
                .append(lobby.getUser2().getUsername())
                .append(",")
                .append(lobby.getCode())
                .append("]");
        return new ResponseEntity<>(response.toString(),HttpStatus.OK);
    }

    @PutMapping("/{username}/code")
    public ResponseEntity<String> updateCode(@PathVariable("username") String username,@RequestParam String code) {
        Lobby lobby = lobbies.stream().filter(l -> l.getUser1().getUsername().equals(username)).findFirst().orElse(null);
        if(lobby == null) {
            return new ResponseEntity<>("404",HttpStatus.NOT_FOUND);
        }

        lobby.setCode(code);
        LobbyDAO.updateCode(lobby,code);
        return new ResponseEntity<>("200",HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteLobby(@PathVariable("username") String username) {
        Lobby lobby = lobbies.stream().filter(l -> l.getUser1().getUsername().equals(username)).findFirst().orElse(null);
        if(lobby == null) {
            return new ResponseEntity<>("404-DELETE",HttpStatus.NOT_FOUND);
        }

        lobbies.remove(lobby);
        LobbyDAO.deleteLobby(lobby);
        LobbyDAO.commit();
        return new ResponseEntity<>("200-DELETE",HttpStatus.OK);
    }
}
