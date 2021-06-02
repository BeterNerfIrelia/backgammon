package fii.backgammon.server.client.game;

import fii.backgammon.server.client.game.util.Dice;
import fii.backgammon.server.client.game.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

    private List<Integer> board = new ArrayList<>(25);
    private Pair<Integer,Integer> whites; // first = pieces that are taken; second = pieces that got out
    private Pair<Integer,Integer> blacks;
    private Dice dice;

    public Board() {
        board = Collections.nCopies(25,0);
        board.set(1,-2);
        board.set(6,5);
        board.set(8,3);
        board.set(12,-5);
        board.set(13,5);
        board.set(17,-3);
        board.set(19,-5);
        board.set(24,2);
        whites.setPair(new Pair<Integer,Integer>(0,0));
        blacks.setPair(new Pair<Integer,Integer>(0,0));
    }

    public void init() {
        initPieces();
        initPiecesMeta();
    }

    private void initPieces() {
        board = Collections.nCopies(25,0);
        board.set(1,-2);
        board.set(6,5);
        board.set(8,3);
        board.set(12,-5);
        board.set(13,5);
        board.set(17,-3);
        board.set(19,-5);
        board.set(24,2);
    }

    private void initPiecesMeta() {
        whites.setPair(new Pair<Integer,Integer>(0,0));
        blacks.setPair(new Pair<Integer,Integer>(0,0));
    }

    public void flip() {
        for(int i=1;i<=24;++i)
            board.set(i,board.get(i) * (-1));
    }
}
