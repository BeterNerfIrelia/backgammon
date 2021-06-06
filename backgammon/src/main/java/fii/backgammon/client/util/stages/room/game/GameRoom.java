package fii.backgammon.client.util.stages.room.game;

import java.net.Socket;
import java.util.Scanner;

import fii.backgammon.client.util.Messages;
import fii.backgammon.client.util.entity.Lobby;

import fii.backgammon.client.util.stages.LobbyMenu;
import fii.backgammon.client.util.stages.Register;
import fii.backgammon.client.util.stages.room.game.game.Board;
import fii.backgammon.client.util.stages.room.game.game.util.Colour;
import fii.backgammon.client.util.stages.room.game.game.util.Pair;
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

        String piece = board.getWhiteCircle();

        /*
        LobbyMenu.newlines();
        System.out.println("Your commands are:\n\t- roll\n\t- move <column> <dice_value>\n\t- put <dice_value> if you have a captured piece\n\t- remove <column> if all your pieces are in your base(bottom left or 19-24 columns)\n\t- exit");
        */

        Boolean invalid = false;
        int response = 0;
        Pair<Integer,Integer> responsePair = new Pair<>();
        responsePair.setPair(0,0);
        while (true) {
            /*
            System.out.println("You have these pieces: " + piece);
            board.drawBoard();
            */

            defaultText(board,piece, invalid);

            String command = read();
            String[] tokens = command.split(" ");

            if(responsePair.getFirst() == 0) {
                System.out.println("You need to roll first before inserting other commands");
                try {
                    Thread.sleep(1500);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                }
            }
            if(responsePair.getSecond() == -1) {
                invalid = true;
                continue;
            }
            if(responsePair.getFirst() == 1 && tokens[0].equals("roll")) {
                System.out.println("You already rolled. Please use your dice before rolling again");
                try {
                    Thread.sleep(1500);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                }
            }

            if(responsePair.getSecond() == -2) {
                System.out.println("That is an invalid move.");
                try {
                    Thread.sleep(1500);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                }
            }

            if(responsePair.getSecond() == -3) {
                System.out.println("That column is occupied or you don't have captured pieces");
                try {
                    Thread.sleep(1500);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                }
            }

            if(responsePair.getSecond() == -4) {
                System.out.println("You don't have a piece in that column or don't have all pieces in your home");
                try {
                    Thread.sleep(1500);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                }
            }

            if(responsePair.getSecond() == 5)
                return;

            response = executeCommand(tokens,board, game);
            if(response == 1)
                responsePair.setFirst(response);
            else
                responsePair.setSecond(response);
            if(board.getDice().size() == 0)
                responsePair.setFirst(0);
        }

    }

    public static int executeCommand(String[] tokens, Board board, Game game) {
        switch(tokens[0]) {
            case "roll": {
                var rolls = board.roll();
                if(rolls.size() == 4) {
                    game.setState(board.getColour() == Colour.WHITE ? "white" + 4 : "black" + 4);
                }
                else {
                    game.setState(board.getColour() == Colour.WHITE ? "white" + 2 : "black" + 2);
                }

                StringBuilder sb = new StringBuilder("[" + rolls.get(0));
                for(int i=1; i<rolls.size(); ++i)
                    sb.append(",").append(rolls.get(i));
                sb.append("]");
                System.out.println("You rolled: " + sb.toString());
                return 1;
            }

            case "move": {
                if(!board.hasOffPieces()) {
                    if(board.canMove(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]))) {
                        game.setDice("[" + tokens[1] + "," + tokens[2] + "]");
                        var state = game.getState();
                        state += "move";
                        game.setState(state);
                        board.getDice().remove(Integer.parseInt(tokens[2]));
                        return 2;
                    }
                    else {
                        return -2;
                    }
                }
                else {
                    return -2;
                }
            }

            case "put": {
                if(board.hasOffPieces()) {
                    if(board.getDice().contains(Integer.parseInt(tokens[1])))
                        if(board.canPutPiece(Integer.parseInt(tokens[1]))) {
                            game.setDice("[" + tokens[1] + ",0]");
                            var state = game.getState();
                            state += "put";
                            game.setState(state);
                            board.getDice().remove(Integer.parseInt(tokens[1]));
                            return 3;
                        }
                }
                return -3;
            }

            case "remove": {
                if(!board.hasPiecesOutsideBase()) {
                    if(board.canRemove(board.getDice()))
                        if(board.canRemovePiece(Integer.parseInt(tokens[1]))) {
                            game.setDice("[0," + tokens[1]);
                            var state = game.getState();
                            state += "remove:";
                            game.setState(state);
                            board.getDice().remove(Integer.parseInt(tokens[1]));
                        }
                }
                return -4;
            }

            case "concede": {
                return 5;
            }

            default: {
                //TODO invalid command
                return -1;
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

    public static void defaultText(Board board, String piece, Boolean invalid) {
        LobbyMenu.newlines();
        System.out.println("Your commands are:\n\t- roll\n\t- move <column> <dice_value>\n\t- put <dice_value>, if you have a captured piece\n\t- remove <column>, if all your pieces are in your base(bottom left or 19-24 columns)\n\t- concede");

        System.out.println("You have these pieces: " + piece);
        board.drawBoard();

        if(invalid) {
            System.out.println("Invalid command. Please input a valid command");
            invalid = !invalid;
        }
    }

}
