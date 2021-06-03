package fii.backgammon.client.util.stages.room;

import fii.backgammon.client.util.Messages;
import fii.backgammon.client.util.entity.Lobby;
import fii.backgammon.client.util.entity.User;
import fii.backgammon.client.util.stages.LobbyMenu;
import fii.backgammon.client.util.stages.Register;
import fii.backgammon.client.util.stages.room.game.GameRoom;

import java.net.Socket;
import java.util.Scanner;

public class LobbyRoomCreator {

    public static void run(Socket socket, User user) {

        Lobby lobby = createLobby(user, socket);
        while(true) {
            System.out.println("\n\n\tThis is your lobby. Your friend needs to enter your username to join the lobby\n\t\t\t\t" + user.getUsername());
            System.out.println("\n");
            System.out.println("\tPlayer 1: " + user.getUsername());
            System.out.println("\n\tPlayer 2:" + (lobby.getUser2() == null ? ".........." : (" " + lobby.getUser2().getUsername())));
            System.out.println("\n\n1. Start");
            System.out.println("\n2. Refresh");
            System.out.println("\n3. Back");

            String command = read();

            switch (command) {
                case "1": {
                    if(lobby.getUser2() == null) {
                        System.out.println("There isn't a second player connected");
                        Lobby lobby2 = updateLobby(lobby,socket);
                        lobby = lobby2;
                        continue;
                    }

                    updateCode(lobby,socket);
                    GameRoom.run(lobby,socket,0);
                    return;
                }

                case "2": {
                    LobbyMenu.newlines();
                    Lobby lobby2 = updateLobby(lobby,socket);
                    lobby = lobby2;
                    break;

                }

                case "3": {
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

    public static Lobby createLobby(User user, Socket socket) {
        String request = "lobby.create ";
        StringBuilder userData = new StringBuilder("[").
                                     append(user.getId()).
                                     append(",").
                                     append(user.getUsername()).
                                     append("]");
        request += userData;

        Messages.send(socket,request);

        String response = Register.readSocket(socket);
        String[] data = response.replace("[","").replace("]","").split(",");
        User userLobby = new User(data[0],data[1]);
        String code = data[2];

        return new Lobby(userLobby,code);
    }

    public static Lobby updateLobby(Lobby lobby, Socket socket) {
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
        }
        return lobby;

    }

    public static void updateCode(Lobby lobby, Socket socket) {
        String request = "lobby.code " + lobby.getUser1().getUsername();

        Messages.send(socket,request);

        Register.readSocket(socket);
    }
}
