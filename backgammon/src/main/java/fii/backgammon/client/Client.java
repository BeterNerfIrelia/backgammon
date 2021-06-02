package fii.backgammon.client;

import fii.backgammon.client.util.User;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String address = "127.0.0.1";
    private int port = 8082;
    private Socket socket;
    private User user = new User();

    public Client(String address,int port) {
        this.address = address;
        this.port = port;
    }

    public Client() {
    }

    public void connect() {
        try {
            socket = new Socket(address,port);
            System.out.println("Connected");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
