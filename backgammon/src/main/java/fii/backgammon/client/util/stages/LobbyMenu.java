package fii.backgammon.client.util.stages;

import fii.backgammon.client.util.Messages;
import fii.backgammon.client.util.entity.User;
import fii.backgammon.client.util.stages.room.LobbyRoomCreator;
import fii.backgammon.client.util.stages.room.LobbyRoomJoined;

import java.net.Socket;
import java.util.Scanner;

public class LobbyMenu {

    public static void run(Socket socket, String username) {
        User user = getUserInfo(username,socket);
        StringBuilder message = new StringBuilder("Welcome to the lobby menu! Here you can either create or join a lobby. Please insert the number of the command you want to proceed with.");
        message.append("\n\n1. Create lobby");
        message.append("\n\n2. Join lobby");
        message.append("\n\n3. Exit");
        while(true) {
            newlines();
            System.out.println(message);

            System.out.println("\n\nPlease insert the corresponding number to your option:");
            String command = LobbyMenu.read();

            switch (command) {
                case "1": {
                    newlines();
                    LobbyRoomCreator.run(socket,user);
                    break;
                }

                case "2": {
                    newlines();
                    LobbyRoomJoined.run(socket,user);
                    break;
                }

                case "3": {
                    System.out.println("\nGoodbye!");
                    Messages.send(socket,null);
                    return;
                }

                default: {
                }
            }
        }
    }

    public static void newlines() {
        for(int i=0;i<10;++i)
            System.out.println();
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

    public static User getUserInfo(String username, Socket socket) {
        String request = "get " + username;

        Messages.send(socket,request);

        String response = Register.readSocket(socket);

        String[] tokens = response.replace("[","").replace("]","").split(",");
        String id = tokens[0];
        String uname = tokens[1];

        return new User(id,uname);
    }
}
