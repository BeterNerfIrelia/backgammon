package fii.backgammon.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread{
    private Socket socket;
    private static final int SO_TIMEOUT = 5_184_400;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            this.socket.setSoTimeout(SO_TIMEOUT);
            while(true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String request = in.readLine();

                if(request == null)
                    break;

                PrintWriter out = new PrintWriter(socket.getOutputStream());

                String response = this.interpretCommand(request);
                out.println(response);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String interpretCommand(String command) {
        return null;
    }
}
