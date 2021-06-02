package fii.backgammon.client.util.entity;

public class Lobby {
    User user1 = null;
    User user2 = null;
    String code;

    public Lobby() {
    }

    public Lobby(User user1) {
        this.user1 = user1;
    }

    public Lobby(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public Lobby(User user1, String code) {
        this.user1 = user1;
        this.code = code;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
