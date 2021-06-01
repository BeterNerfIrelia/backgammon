package fii.backgammon.server.handlers.dao;

import fii.backgammon.server.handlers.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static Connection connection;

    private UserDAO() {
    }

    public static void setConnection(Connection connection) {
        UserDAO.connection = connection;
    }

    public static void commit() {
        try {
            if(!connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(throwables.getMessage());
        }
    }

    public static List<User> getAllUsers() {
        List<User> tmp = new ArrayList<>();
        try{
            String id;
            String username;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from USERS");
            while(rs.next()) {
                id = rs.getString(1);
                username = rs.getString(2);
                tmp.add(new User(id,username));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(throwables.getMessage());
        }
        return tmp;
    }

    public static void insertUser(User user) {
        try {
            String id = user.getId();
            String username = user.getUsername();
            //String insert = "INSERT INTO USERS VALUES(" + id + "," + username + ")";
            String insert = "INSERT INTO USERS VALUES(?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insert);
            pstmt.setString(1,id);
            pstmt.setString(2,username);

            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(throwables.getMessage());
        }
    }

    public static void deleteUser(User user) {
        try{
            String delete = "DELETE FROM USERS WHERE user_id=? AND username=?";
            PreparedStatement pstmt = connection.prepareStatement(delete);
            pstmt.setString(1,user.getId());
            pstmt.setString(2,user.getUsername());
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println(throwables.getMessage());
        }
    }
}
