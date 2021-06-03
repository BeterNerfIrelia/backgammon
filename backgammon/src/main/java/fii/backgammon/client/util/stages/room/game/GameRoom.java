package fii.backgammon.client.util.stages.room.game;

import java.net.Socket;
import java.util.Scanner;

import fii.backgammon.client.util.entity.Lobby;

import fii.backgammon.client.util.stages.LobbyMenu;
import fii.backgammon.client.util.stages.room.game.game.Board;
import fii.backgammon.client.util.stages.room.game.game.util.Colour;

public class GameRoom {

    public static void run(Lobby lobby, Socket socket, int colour) {

        Colour color = ((colour == 0) ? Colour.WHITE : Colour.BLACK);

        Board board = new Board(color);
        String piece = board.getWhiteCircle();
        String piece2 = board.getBlackCircle();

        LobbyMenu.newlines();
        System.out.println("Your commands are:\n\t- roll\n\t- move <column> <dice_value>");


        while (true) {
            System.out.println("You have these pieces: " + piece);
            board.drawBoard();

            String command = read();
            String[] tokens = command.split(" ");
            switch(tokens[0]) {
                case "roll": {
                    var rolls = board.roll();
                    StringBuilder sb = new StringBuilder("[" + rolls.get(0));
                    for(int i=1; i<rolls.size(); ++i)
                        sb.append(",").append(rolls.get(i));
                    sb.append("]");
                    System.out.println("You rolled: " + sb.toString());
                }

                case "move": {

                }

                default: {
                }
            }

        }

    }

    public static String read()  {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        input = input.trim();

        return input;
    }

}
