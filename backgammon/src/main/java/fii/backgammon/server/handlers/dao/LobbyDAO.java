package fii.backgammon.server.handlers.dao;

import fii.backgammon.server.handlers.entity.Lobby;
import fii.backgammon.server.handlers.entity.User;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LobbyDAO {

    private static Connection connection;

    private LobbyDAO(){
    }

    public static void setConnection(Connection connection) {
        LobbyDAO.connection = connection;
    }

    public static void commit() {
        try{
            if(!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(throwables.getMessage());
        }
    }

    public static List<Lobby> getAllLobbies() {
        List<Lobby> lobbies = new ArrayList<>();
        try {
            String id1;
            String id2;
            String code;
            String username1;
            String username2;

            User user1 = null;
            User user2 = null;

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LOBBY");
            PreparedStatement pstmt = connection.prepareStatement("SELECT USERNAME FROM USERS WHERE USER_ID=?");
            while(rs.next()) {
                id1 = rs.getString(1);
                id2 = rs.getString(2);
                code = rs.getString(3);

                pstmt.setString(1,id1);
                ResultSet prs = pstmt.executeQuery();
                prs.next();
                username1 = prs.getString(1);
                user1 = new User(id1, username1);

                pstmt.setString(1,id2);
                ResultSet prs2 = pstmt.executeQuery();
                if(prs2.next()) {
                    username2 = prs2.getString(1);
                    user2 = new User(id2, username2);
                }
                lobbies.add(new Lobby(user1, user2,code));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(throwables.getMessage());
            return null;
        }

        return lobbies;
    }

    public static void insertLobby(Lobby lobby) {
        try {
            User user1 = lobby.getUser1();
            User user2 = lobby.getUser2();
            String code = lobby.getCode();
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO LOBBY VALUES(?,?,?)");
            pstmt.setString(1,user1.getId());
            pstmt.setString(2,user2 == null ? null : user2.getId());
            pstmt.setString(3,code);
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteLobby(Lobby lobby) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM LOBBY WHERE USER_ID1=?");
            pstmt.setString(1,lobby.getUser1().getId());
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addUser(Lobby lobby, User user2) {
        try {
            lobby.setUser2(user2);
            PreparedStatement pstmt = connection.prepareStatement("UPDATE LOBBY SET USER_ID2=? WHERE USER_ID1=?");
            pstmt.setString(1, user2.getId());
            pstmt.setString(2,lobby.getUser1().getId());
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
