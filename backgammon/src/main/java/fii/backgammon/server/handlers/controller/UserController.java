package fii.backgammon.server.handlers.controller;

import fii.backgammon.server.handlers.dao.UserDAO;
import fii.backgammon.server.handlers.entity.User;
import fii.backgammon.server.handlers.util.DBConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private List<User> users = new ArrayList<>();

    public UserController() {
        Connection connection = DBConnection.getConnection();
        UserDAO.setConnection(connection);
        users = UserDAO.getAllUsers();
    }

    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) {
        return users.stream().
                     filter(user -> user.getUsername().equals(username)).
                     findFirst().
                     orElse(null);
    }

    @PostMapping(value="/username")
    public ResponseEntity<String> createUser(@RequestParam String username) {
        User tmp = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        if(tmp != null) {
            return new ResponseEntity<>("302",HttpStatus.FOUND);
        }
        User user = new User(username);
        users.add(user);
        UserDAO.insertUser(user);
        UserDAO.commit();
        return new ResponseEntity<>("201", HttpStatus.CREATED);
    }

    @DeleteMapping(value="/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        User user = users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
        if(user == null) {
            return new ResponseEntity<>("404",HttpStatus.NOT_FOUND);
        }
        users.remove(user);
        UserDAO.deleteUser(user);
        UserDAO.commit();
        return new ResponseEntity<>("200",HttpStatus.OK);
    }
}
