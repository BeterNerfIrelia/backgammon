package fii.backgammon.client.util.stages;

import java.util.Locale;
import java.util.Scanner;

public class Register {
    public static void run() {
        boolean exit = false;
        while(!exit) {
            System.out.println("Welcome to Backgammon\n");
            System.out.println("Please insert your username. It is limited to 100 characters! Or you can type \"exit\" to exit the program");
            System.out.println("Your username:");

            String username = read();

            if(username.equals("exit")) {
                exit = true;
                continue;
            }

            System.out.println("Your username is " + username);
            exit=true;
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
}
