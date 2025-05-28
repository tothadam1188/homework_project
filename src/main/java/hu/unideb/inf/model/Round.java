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
}
