package fii.backgammon.server.handlers.entity;

import java.util.Random;

public class Lobby {
    User user1;
    User user2;
    String code;

    public Lobby() {
    }

    public Lobby(User user1) {
        this.user1 = user1;
        this.code = Lobby.generateCode();
    }

    public Lobby(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.code = Lobby.generateCode();
    }

    public Lobby(User user1, User user2, String code) {
        this.user1 = user1;
        this.user2 = user2;
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

    public static String generateCode() {
        String code = "";
        Random random = new Random();
        for(int i=0;i<8;++i)
            code += random.nextInt(10);
        return code;
    }
}
