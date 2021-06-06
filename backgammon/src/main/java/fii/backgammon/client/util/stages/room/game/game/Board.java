package fii.backgammon.client.util.stages.room.game.game;

import fii.backgammon.client.util.stages.room.game.game.util.Colour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Board {

    private List<Integer> board = new ArrayList<>(25);
    List<Integer> res = new ArrayList<>();

    private Integer whites;
    private Integer blacks;
    private Colour colour;
    private Random random = new Random();

    private final String whiteCircle = "0";
    private final String blackCircle = "x";

    public String getWhiteCircle() {
        return whiteCircle;
    }

    public String getBlackCircle() {
        return blackCircle;
    }

    public Board() {
        putZero();
        initPieces();
        initOffPieces();

    }

    public Board(Colour colour) {
        putZero();
        initPieces();
        initOffPieces();
        this.colour = colour;

        if(colour == Colour.BLACK)
            this.flip();

        /*
        whiteCircle = colour == Colour.WHITE ? "0" : "x";
        blackCircle = colour == Colour.WHITE ? "x" : "0";
         */
    }

    public List<Integer> getBoard() {
        return board;
    }

    public void setBoard(List<Integer> board) {
        this.board = board;
    }

    public Colour getColour() {
        return colour;
    }

    public void setBoard(String board) {
        String[] data = board.replace("[","").replace("]","").split(",");
        for(int i=0;i<=24;++i)
            this.board.set(i,Integer.parseInt(data[i]));
        this.whites = Integer.parseInt(data[25]);
        this.blacks = Integer.parseInt(data[26]);

    }

    public List<Integer> getRes() {
        return res;
    }

    public void setRes(List<Integer> res) {
        this.res = res;
    }

    public void flip() {
        for(int i=1; i<=12;++i) {
            int tmp = board.get(i);
            board.set(i, board.get(24-i+1));
            board.set(24-i+1,tmp);
        }
    }

    public void receiveBoard(List<Integer> board) {
        this.board = board;
        this.flip();
    }

    private void initPieces() {
        putZero();
        board.set(1,2);
        board.set(6,-5);
        board.set(8,-3);
        board.set(12,5);
        board.set(13,-5);
        board.set(17,3);
        board.set(19,5);
        board.set(24,-2);
    }

    private void initOffPieces() {
        whites = 0;
        blacks = 0;
    }

    public void init() {
        initPieces();
        initOffPieces();
    }

    private int maxHeight() {
        int maxi = 0;
        for(int i : board)
            if(Math.abs(i) > maxi)
                maxi = i > 0 ? i : -i;

        return maxi;
    }

    private void drawBottomMargin(StringBuilder sb) {
        sb.append("--24--23--22--21--20--19--BW--18--17--16--15--14--13--\n");
    }

    private void drawTopMargin(StringBuilder sb) {
        sb.append("--1---2---3---4---5---6---BB---7---8---9--10--11--12--\n");
    }

    private void drawTopLine(int line, StringBuilder sb) {
        for(int column = 1; column <= 6; ++column) {
            drawSegment(line,column,sb);
        }
        sb.append("||  |");
        for(int column = 7; column<=12; ++column){
            drawSegment(line,column,sb);
        }
    }

    private void drawBottomLine(int line, StringBuilder sb) {
        for(int j = 24; j>=19; --j) {
            drawSegment(line,j,sb);
        }
        sb.append("||  |");
        for(int j = 18;j>=13;--j) {
            drawSegment(line,j,sb);
        }
    }

    private void drawSegment(int line, int column, StringBuilder sb) {
        sb.append("| ");
        if(Math.abs(board.get(column)) >= line) {
            sb.append(board.get(column) > 0 ? whiteCircle : blackCircle);
        }
        else {
            sb.append(" ");
        }
        sb.append(" ");
    }

    public void drawBoard() {
        StringBuilder sb = new StringBuilder();
        drawTopMargin(sb);
        int maxi = maxHeight();
        for(int i=1;i<=maxi; ++i) {
            drawTopLine(i,sb);
            sb.append("|\n");
        }
        sb.append("=======================================================\n");
        for(int i=maxi;i>=1;--i) {
            drawBottomLine(i,sb);
            sb.append("|\n");
        }
        drawBottomMargin(sb);

        System.out.println(sb);
    }

    private void putZero() {
        this.board.clear();
        for(int i=0;i<25;++i)
            this.board.add(0);
    }

    public List<Integer> roll() {
        //List<Integer> res = new ArrayList<>();
        res.clear();
        int d1 = random.nextInt(6) + 1;
        int d2 = random.nextInt(6) + 1;

        if(d1 == d2) {
            res.add(d1);
            res.add(d1);
            res.add(d1);
            res.add(d1);
        }
        else {
            res.add(d1);
            res.add(d2);
        }

        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[0");
        for(int i=1;i<=24;++i)
            sb.append(",").append(this.board.get(i));
        sb.append(",").append(whites).append(",").append(blacks).append("]+[");
        if(res.size() == 0) {
            sb.append(0).append("]");
        }
        else {
            sb.append(res.get(0));
            for(int i=1;i<res.size();++i) {
                sb.append(",").append(res.get(i));
            }
            sb.append("]");
        }

        return sb.toString();
    }

    public String getBoardString() {
        StringBuilder sb = new StringBuilder("[0");
        for(int i=1;i<=24;++i)
            sb.append(",").append(this.board.get(i));
        sb.append(",").append(whites).append(",").append(blacks).append("]");

        return sb.toString();
    }

    public String getDiceString() {
        StringBuilder sb = new StringBuilder("[");
        if(res.size() == 0) {
            sb.append(0).append("]");
        }
        else {
            sb.append(res.get(0));
            for(int i=1;i<res.size();++i) {
                sb.append(",").append(res.get(i));
            }
            sb.append("]");
        }

        return sb.toString();
    }

    public boolean hasOffPieces() {
        if(this.colour == Colour.WHITE)
            return whites > 0;
        return blacks > 0;
    }

    public boolean canMove(int column, int dist) {
        if(column < 1 || column > 24) {
            return false;
        }

        if(board.get(column) == 0) {
            return false;
        }

        if(this.colour == Colour.WHITE) {
            if(board.get(column) < 0)
                return false;
            if(column + dist > 24)
                return false;
            if(board.get(column+dist) < -1)
                return false;
            return true;
        }
        else {
            if(board.get(column) > 0)
                return false;
            if(column + dist > 24)
                return false;
            if(board.get(column+dist) > 1)
                return false;
            return true;
        }
    }

    public boolean hasPiecesOutsideBase() {
        if(hasOffPieces())
            return true;
        for(int i=1;i<=18;++i)
            if(this.colour == Colour.WHITE) {
                if(board.get(i) > 0)
                    return true;
            }
        else {
                if(board.get(i) < 0)
                    return true;
            }
        return false;
    }

    public boolean canPutPiece(int column) {
        if(this.colour == Colour.WHITE) {
            if(column < 1 || column >6)
                return false;
            if(board.get(column) < -1)
                return false;
            return true;
        }
        else {
            if(column < 1 || column >6)
                return false;
            if(board.get(column) > 1)
                return false;
            return true;

        }
    }

    public boolean canRemove() {
        if(this.colour == Colour.WHITE) {
            for(int i=1;i<=6;++i)
                if(board.get(i) >= -1)
                    return true;
        }
        else {
            for(int i=1;i<=6;++i)
                if(board.get(i) <= 1)
                    return true;
        }
        return false;
    }

    public boolean canRemove(List<Integer> dice) {
        if(!canRemove())
            return false;
        if(dice.size() == 4)
            return canRemovePiece(dice.get(0));
        return canRemovePiece(dice.get(0)) || canRemovePiece(dice.get(1));
    }

    public boolean canRemovePiece(int column) {
        if(this.colour == Colour.WHITE) {
            if(column < 19 || column > 24)
                return false;
            if(board.get(column) < 1)
                return false;
            return true;
        }
        else {
            if(column < 19 || column > 24)
                return false;
            if(board.get(column) > -1)
                return false;
            return true;
        }
    }

    public List<Integer> getDice() {
        return res;
    }
}
