package fii.backgammon.client.util;

public class User {

    private String username;
    private boolean connected = false;

    public User(String username, boolean connected) {
        this.username = username;
        this.connected = connected;
    }

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

}
