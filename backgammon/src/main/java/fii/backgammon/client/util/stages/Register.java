package fii.backgammon.client.util.stages;

import fii.backgammon.client.util.Messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;

public class Register {
    public static void run(Socket socket) {
        while(true) {
            System.out.println("Welcome to Backgammon\n");
            System.out.println("Please insert your username. It is limited to 100 characters! Or you can type \"exit\" to exit the program.");
            System.out.println("Your username:");

            String username = read();

            if(username.equals("exit")) {
                System.out.println("Goodbye!");
                Messages.send(socket,null);
                return;
            }


            System.out.println("Your username is " + username);
            StringBuilder sb = new StringBuilder("register ");
            sb.append(username);
            Messages.send(socket,sb.toString());

            String response = Register.readSocket(socket);
            System.out.println("RESPONSE: " + response);
            if(response.equals("302")) {
                System.out.println("\n\nUsername already exists. Please enter another username.\n");
                continue;
            }

            System.out.println("\n\nWelcome, " + username);
            LobbyMenu.run(socket, username);
            break;
        }

    }

    public static String read() {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        if(input.trim().toLowerCase(Locale.ROOT).equals("exit")) {
            return "exit";
        }

        return input.trim();
    }

    public static String readSocket(Socket socket) {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return  in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return "";
    }
}
