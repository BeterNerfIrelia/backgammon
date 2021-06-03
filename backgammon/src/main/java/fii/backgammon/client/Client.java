package fii.backgammon.client;

import java.io.IOException;
import java.net.Socket;


public class Client {
    private String address = "127.0.0.1";
    private int port = 8082;
    private Socket socket;

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
        System.out.println("Disconnected");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public Socket getSocket() {
        return socket;
    }

}
