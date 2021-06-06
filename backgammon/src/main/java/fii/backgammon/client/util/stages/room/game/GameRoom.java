package fii.backgammon.client.util.stages.room.game;

import java.net.Socket;
import java.util.Scanner;

import fii.backgammon.client.util.Messages;
import fii.backgammon.client.util.entity.Lobby;

import fii.backgammon.client.util.stages.LobbyMenu;
import fii.backgammon.client.util.stages.Register;
import fii.backgammon.client.util.stages.room.game.game.Board;
import fii.backgammon.client.util.stages.room.game.game.util.Colour;
import fii.backgammon.server.handlers.entity.Game;

public class GameRoom {

    public static void run(Lobby lobby, Socket socket, int colour) {

        Colour color = ((colour == 0) ? Colour.WHITE : Colour.BLACK);

        Board board = new Board(color);
        Game game = new Game("0","0","0","0");;
        if(colour == 0) {
            game = createGame(board,lobby.getUser1().getId(),socket);
            System.out.println("LEADER: " + game.toString());
        }
        else {
            try {
                Thread.sleep(2000);
                game = refreshGame(lobby.getUser1().getId(),socket);
                System.out.println("JOINER: " + game.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
        }

        System.out.println("GAME: " + "Mere");

        String piece = board.getWhiteCircle();
        String piece2 = board.getBlackCircle();

        LobbyMenu.newlines();
        System.out.println("Your commands are:\n\t- roll\n\t- move <column> <dice_value>\n\t- put <dice_value> if you have a captured piece\n\t- remove <column> if all your pieces are in your base(bottom left or 19-24 columns)\n\t- exit");


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

    public static Game createGame(Board board, String userId,Socket socket) {
        String request = "game.create ";
        StringBuilder sb = new StringBuilder(userId).append("+");

        sb.append(board.toString());
        sb.append("+white");

        request += sb.toString();

        System.out.println("REQUEST: " + request);

        Messages.send(socket,request);

        String response = Register.readSocket(socket);

        System.out.println("RESPONSE: " + response);

        String[] data = response.split("\\+");
        Game tmp = new Game(userId,data[0],data[1],data[2]);

        return tmp;
    }

    public static Game refreshGame(String userId, Socket socket) {
        String request = "game.get " + userId;
        Messages.send(socket,request);

        String response = Register.readSocket(socket);

        System.out.println("RESPONSE: " + response);

        String[] data = response.split("\\+");
        Game tmp = new Game(userId,data[0],data[1],data[2]);

        return tmp;

    }

    public static Game updateGame(Game game, Socket socket) {
        String request = "game.update " + game.getUserId();
        StringBuilder sb = new StringBuilder("+").append(game.getBoard())
                                .append("+").append(game.getDice())
                                .append("+").append(game.getState());

        request += sb.toString();
        System.out.println("UPDATE REQUEST: " + request);
        Messages.send(socket,request);

        String response = Register.readSocket(socket);

        System.out.println("UPDATE RESPONSE: " + response);

        String[] data = response.split("\\+");

        game.setBoard(data[0]);
        game.setDice(data[1]);
        game.setState(data[2]);

        return game;

    }

}
