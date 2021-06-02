package fii.backgammon.server.client.game;

import fii.backgammon.server.client.game.util.Colour;
import fii.backgammon.server.handlers.entity.Lobby;
import fii.backgammon.server.handlers.entity.User;

public class Player {
    User user;
    Lobby lobby;
    Board board;
    Colour colour;

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }
}
