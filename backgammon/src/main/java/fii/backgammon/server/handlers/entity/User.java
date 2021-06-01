package fii.backgammon.server.handlers.entity;

import java.util.UUID;

public class User {

    private String id;
    private String username;

    public User() {
    }

    public User(String username) {
        this.id = UUID.randomUUID().toString().replace("-","");
        this.username = username;
    }

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
