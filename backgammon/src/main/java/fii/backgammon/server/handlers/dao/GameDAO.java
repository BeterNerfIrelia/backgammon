package fii.backgammon.server.handlers.dao;

import fii.backgammon.server.handlers.entity.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    private static Connection connection;

    private GameDAO(){
    }

    public static void setConnection(Connection connection) {
        GameDAO.connection = connection;
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

    public static List<Game> getAllGames() {
        List<Game> tmp = new ArrayList<>();
        try {
            String userId;
            String board;
            String dice;
            String state;

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM GAMES");
            while(rs.next()) {
                userId = rs.getString(1);
                board = rs.getString(2);
                dice = rs.getString(3);
                state = rs.getString(4);
                tmp.add(new Game(userId,board,dice,state));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(throwables.getMessage());
        }

        return tmp;
    }

    public static void insertGame(Game game) {
        try {
            String userId = game.getUserId();
            String board = game.getBoard();
            String dice = game.getDice();
            String state = game.getState();
            String insert = "INSERT INTO GAMES VALUES(?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insert);

            pstmt.setString(1,userId);
            pstmt.setString(2,board);
            pstmt.setString(3,dice);
            pstmt.setString(4,state);

            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteGame(Game game) {
        try {
            String userId = game.getUserId();
            String delete = "DELETE FROM GAMES WHERE user_id=?";
            PreparedStatement pstmt = connection.prepareStatement(delete);
            pstmt.setString(1,userId);

            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(throwables.getMessage());
        }
    }

    public static void updateGame(Game game) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE GAMES SET BOARD=?,DICE=?,STATE=? WHERE USER_ID=?");
            pstmt.setString(1,game.getBoard());
            pstmt.setString(2,game.getDice());
            pstmt.setString(3,game.getState());
            pstmt.setString(4,game.getUserId());

            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(throwables.getMessage());
        }
    }
}
