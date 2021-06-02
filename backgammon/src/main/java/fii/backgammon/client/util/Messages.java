package fii.backgammon.client.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Messages {

    private String message;

    public Messages() {
    }

    public Messages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void print() {
        System.out.println(this.message);
    }

    public static void send(Socket socket, String response) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(response);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
