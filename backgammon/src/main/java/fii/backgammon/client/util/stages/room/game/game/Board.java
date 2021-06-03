package fii.backgammon.client.util.stages.room.game.game;

import fii.backgammon.client.util.stages.room.game.game.util.Colour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {

    private List<Integer> board = new ArrayList<>(25);
    private Integer whites;
    private Integer blacks;
    private Colour colour;
    private Random random = new Random();

    private String whiteCircle;
    private String blackCircle;

    public String getWhiteCircle() {
        return whiteCircle;
    }

    public String getBlackCircle() {
        return blackCircle;
    }

    public Board() {
        putZero();
        board.set(1,-2);
        board.set(6,5);
        board.set(8,3);
        board.set(12,-5);
        board.set(13,5);
        board.set(17,-3);
        board.set(19,-5);
        board.set(24,2);
        whites = 0;
        blacks = 0;

        whiteCircle = "0";
        blackCircle = "x";
    }

    public Board(Colour colour) {
        putZero();
        board.set(1,-2);
        board.set(6,5);
        board.set(8,3);
        board.set(12,-5);
        board.set(13,5);
        board.set(17,-3);
        board.set(19,-5);
        board.set(24,2);

        whiteCircle = colour == Colour.WHITE ? "0" : "x";
        blackCircle = colour == Colour.WHITE ? "x" : "0";
    }

    private void initPieces() {
        putZero();
        board.set(1,-2);
        board.set(6,5);
        board.set(8,3);
        board.set(12,-5);
        board.set(13,5);
        board.set(17,-3);
        board.set(19,-5);
        board.set(24,2);
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

    private void drawTopMargin(StringBuilder sb) {
        sb.append("--24--23--22--21--20--19--BB--18--17--16--15--14--13--\n");
    }

    private void drawBottomMargin(StringBuilder sb) {
        sb.append("--1---2---3---4---5---6---BW---7---8---9--10--11--12--\n");
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
            sb.append(board.get(column) < 0 ? whiteCircle : blackCircle);
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
        List<Integer> res = new ArrayList<>();
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
}
