package fii.backgammon.server.client.game.util;

import java.util.ArrayList;
import java.util.List;

public class BoardHandler {

    private String arg;
    String[] tokens;

    private List<Integer> board = new ArrayList<>();
    private int whites;
    private int blacks;
    private Pair<Integer,Integer> dice = new Pair<>();
    private String state;

    public BoardHandler(String arg) {
        this.arg = arg;
        tokens = arg.split("\\+");

        this.board = getBoard(tokens[1]);
        this.dice = getDice(tokens[2]);
        this.state = setState(tokens[3]);
    }

    private void setArg() {
        StringBuilder sb = new StringBuilder(tokens[0]).append("+[0");
        for(int i=1;i<=24;++i)
            sb.append(",").append(board.get(i));
        sb.append(",").append(this.whites).append(",").append(this.blacks);
        sb.append("]+[");
        sb.append(dice.getFirst()).append(",").append(dice.getSecond()).append("]+");
        sb.append(state);

        this.arg = sb.toString();
    }

    public String getGame() {
        if(arg.endsWith("4") || arg.endsWith("2"))
            return arg;
        if(arg.endsWith("move"))
            executeMove();
        if(arg.endsWith("put"))
            executePut();
        if(arg.endsWith("remove"))
            executeRemove();

        setArg();
        return arg;
    }

    private String setState(String state) {
        StringBuilder sb = new StringBuilder(state);

        for(int i=0;i<state.length();++i) {
            char c = state.charAt(i);
            if(c >= '0' && c <= '9') {
                int n = c - '0';
                n--;
                sb.setCharAt(i, getNumber(n));
                break;
            }
        }

        return sb.toString();
    }

    private char getNumber(int n) {
        switch(n) {
            case 9:
                return '9';
            case 8:
                return '8';
            case 7:
                return '7';
            case 6:
                return '6';
            case 5:
                return '5';
            case 4:
                return '4';
            case 3:
                return '3';
            case 2:
                return '2';
            case 1:
                return '1';
            default:
                return '0';
        }
    }

    private Pair<Integer,Integer> getDice(String dice) {
        String[] data = dice.replace("[","").replace("]","").split(",");
        Pair<Integer,Integer> pair = new Pair<>(Integer.parseInt(data[0]),Integer.parseInt(data[1]));
        this.dice = pair;

        return pair;
    }

    private List<Integer> getBoard(String board) {
        List<Integer> tmp = new ArrayList<>();
        String[] data = board.replace("[","").replace("]","").split(",");
        for(int i=0;i<=24;++i)
            tmp.add(Integer.parseInt(data[i]));

        this.whites = Integer.parseInt(data[25]);
        this.blacks = Integer.parseInt(data[26]);
        this.board = tmp;

        return tmp;
    }

    private void executeMove() {
        int piece = board.get(dice.getFirst());
        int move = dice.getSecond();
        if(board.get(piece) > 0) {
            board.set(piece,board.get(piece) - 1);
            board.set(piece + move,board.get(piece + move) + 1);
        }
        else {
            board.set(piece,board.get(piece) + 1);
            board.set(piece + move,board.get(piece + move) - 1);
        }
    }

    private void executePut() {
        if(state.contains("white")) {
            board.set(dice.getFirst(),board.get(dice.getFirst()) + 1);
        }
        else {
            board.set(dice.getFirst(),board.get(dice.getFirst()) - 1);
        }
    }

    private void executeRemove() {
        if(state.contains("white")) {
            board.set(dice.getSecond() + 19 - 1,board.get(dice.getSecond() + 19 - 1) - 1);
        }
        else {
            board.set(dice.getSecond() + 19 - 1,board.get(dice.getSecond() + 19 - 1) + 1);
        }
    }
}
