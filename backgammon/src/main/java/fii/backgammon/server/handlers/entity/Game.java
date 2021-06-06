package fii.backgammon.server.handlers.entity;

public class Game {
    private String userId;
    private String board;
    private String dice;
    private String state;

    public Game() {
    }

    public Game(String userId, String board, String dice, String state) {
        this.userId = userId;
        this.board = board;
        this.dice = dice; // A pair of [column, dice]
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getDice() {
        return dice;
    }

    public void setDice(String dice) {
        this.dice = dice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setGame(Game game) {
        this.userId = game.getUserId();
        this.board = game.getBoard();
        this.dice = game.getDice();
        this.state = game.getState();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                            .append(this.board).append("+")
                            .append(this.dice).append("+")
                            .append(this.state);
        return sb.toString();
    }
}
