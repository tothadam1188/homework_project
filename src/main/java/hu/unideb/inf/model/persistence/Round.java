package hu.unideb.inf.model.persistence;

import game.State;

/**
 * Represents a saved game round including player info, board state, and game status.
 */
public class Round {
    private String player1Name;
    private String player2Name;
    private int rounds;
    private String board;
    private State.Status status;

    public Round() {
    }

    /**
     * Creates a new Round with player names, number of rounds, board snapshot, and game status.
     * @param player1Name the name of Player 1
     * @param player2Name the name of Player 2
     * @param rounds the number of rounds it took for the game to end
     * @param board the string representation of the board when the game ended
     * @param status the status of the game when the game ended
     */
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
