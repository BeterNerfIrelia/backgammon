package fii.backgammon.client.util.stages.room;

import fii.backgammon.client.util.Messages;
import fii.backgammon.client.util.entity.Lobby;
import fii.backgammon.client.util.entity.User;
import fii.backgammon.client.util.stages.Register;
import fii.backgammon.client.util.stages.room.game.GameRoom;

import java.net.Socket;
import java.util.Scanner;

public class LobbyRoomJoined {

    public static void run(Socket socket, User user) {
        Lobby lobby = new Lobby();
        lobby.setUser2(user);
        while(true) {
            System.out.println("\n\t\tYou need to insert the username of your friend to enter their lobby");
            System.out.println("\n\n");
            System.out.println("\t1. Insert username");
            System.out.println("\t2. Back");

            String command = read();

            switch (command) {
                case "1": {
                    System.out.println("You need to type your friends' username here: ");
                    String username = readUsername();

                    int status = updateLobby(lobby,socket,username);
                    if(status != 0) {
                        System.out.println("\n\nLobby does not exist at the moment of search. Plase try again later");
                        continue;
                    }

                    boolean activeGame = lobby.getCode().trim().equals("on");
                    System.out.println("Waiting for the leader to start the game...");
                    while(!activeGame) {
                        Lobby lobby2 = refreshLobby(lobby,socket);
                        lobby = lobby2;
                        activeGame = lobby.getCode().trim().equals("on");
                        if(activeGame) {
                            GameRoom.run(lobby,socket,1);
                            return;
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            System.err.println(e.getMessage());
                        }
                    }


                }

                case "2": {
                    return;
                }

                default:{
                }
            }
        }
    }

    public static String read() {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        input = input.trim();
        if(input.length() != 1) {
            return "4";
        }

        return input;
    }

    public static String readUsername() {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        input = input.trim();

        return input;
    }

    public static int updateLobby(Lobby lobby,Socket socket, String username) {
        String request = "lobby.join ";

        StringBuilder param = new StringBuilder("[")
                .append(username)
                .append(",")
                .append(lobby.getUser2().getId())
                .append(",")
                .append(lobby.getUser2().getUsername())
                .append("]");

        request += param.toString();


        Messages.send(socket,request);

        String response = Register.readSocket(socket);
        if(response == null)
            return 1;

        String[] data = response.replace("[","").replace("]","").split(",");
        User user1 = new User(data[0],data[1]);
        lobby.setUser1(user1);
        lobby.setCode(data[4]);
        return 0;
    }

    public static Lobby refreshLobby(Lobby lobby, Socket socket) {
        String request = "lobby.refresh ";
        String pathVariable = lobby.getUser1().getId();

        request += pathVariable;

        Messages.send(socket,request);

        String response = Register.readSocket(socket);
        if(response.trim().equals("404")) {
            System.out.println("Lobby does not exist");
            return lobby;
        }
        String[] data = response.replace("[","").replace("]","").split(",");
        if(data.length == 5) {
            User user = new User(data[2],data[3]);
            lobby.setUser2(user);
            lobby.setCode(data[4]);
        }
        return lobby;
    }
}
