package fii.backgammon.server;

import fii.backgammon.server.client.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 8082;

    public Server() throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);

            while(true) {
                System.out.println("Waiting for a client...");
                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally {
            if(serverSocket != null) {
                serverSocket.close();
            }
        }
    }
}
