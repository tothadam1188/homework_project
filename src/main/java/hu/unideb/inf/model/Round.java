package hu.unideb.inf.model;

import game.State;

public class Round {
    private String player1Name;
    private String player2Name;
    private int rounds;
    private String board;
    private State.Status status;

    public Round() {
    }

    public Round(String player1Name, String player2Name, int rounds, String board, State.Status status) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.rounds = rounds;
        this.board = board;
        this.status = status;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public int getRounds() {
        return rounds;
    }

    public String getBoard() {
        return board;
    }

    public State.Status getStatus() {
        return status;
    }
}
