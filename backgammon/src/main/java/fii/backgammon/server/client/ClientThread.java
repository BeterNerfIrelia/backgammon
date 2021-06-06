package fii.backgammon.server.client;

import fii.backgammon.server.client.commands.*;
import fii.backgammon.server.handlers.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;

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
                System.out.println("request: " + request);

                if(request.equals("null")) {
                    return;
                }

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

    private String interpretCommand(String request) {
        String[] tokens = request.split(" ");
        String command = tokens[0];
        String arg = tokens[1];
        Command comm;

        switch(command.toLowerCase(Locale.ROOT)) {
            case "register": {
                comm = new RegisterCommand(arg);
                String response = comm.run();
                System.out.println("Response: " + response);
                System.out.println("User: " + arg);
                return response;
            }

            case "get": {
                comm = new GetUserInfo(arg);
                String response = comm.run();
                System.out.println("Response: " + response);
                System.out.println("User: " + arg);
                return response;
            }

            case "lobby.create": {
                String[] userData = arg.replace("[","").replace("]","").split(",");
                User user = new User(userData[0],userData[1]);
                comm = new CreateLobby(user);
                String response = comm.run();
                System.out.println("Response: " + response);
                System.out.println("Arg: " + arg);
                return response;
            }

            case "lobby.refresh": {
                comm = new RefreshLobby(arg);
                String response = comm.run();
                System.out.println("Response: " + response);
                System.out.println("Arg: "+ arg);
                return response;

            }

            case "lobby.join": {
                System.out.println("LOBBY.JOIN: " + arg);
                String[] data = arg.replace("[","").replace("]","").split(",");
                String username = data[0];
                User user2 = new User(data[1],data[2]);
                comm = new JoinLobby(username,user2);
                String response = comm.run();
                System.out.println("Response: " + response);
                System.out.println("Arg: " + arg);
                return response;
            }

            case "lobby.code": {
                System.out.println("LOBBY.CODE");
                comm = new UpdateCode(arg);
                String response = comm.run();
                System.out.println("Response: " + response);
                System.out.println("Arg: "+ arg);
                return response;
            }

            case "game.create": {
                System.out.println("GAME.CREATE ARG: " + arg);
                String[] data = arg.split("\\+");
                comm = new CreateGame(data[0],data[1],data[2],data[3]);
                String response = comm.run();
                System.out.println("RESPONSE: " + response);
                return response;

            }

            case "game.get": {
                System.out.println("GAME.GET ARG: " + arg);
                comm = new GetGame(arg);
                String response = comm.run();
                System.out.println("RESPONSE: " + response);
                return response;
            }

            case "game.update": {
                System.out.println("GAME.UPDATE ARG: " + arg);
                // TODO game logic
                comm = new UpdateGame(arg);
                String response = comm.run();
                System.out.println("GAME.UPDATE RESPONSE: " + response);
                return response;
            }

            default: {
                System.out.println("Unknown command");
                return "Unknown command";
            }
        }
    }
}
