package hu.unideb.inf.model;

import game.State;

public class GameModel implements State{

    private Player currentPlayer;
    private Status status;
    private int boardSize=11;
    private int[][] board;

    /**
     * Initializes the game model with default settings.
     * This constructor sets the current player to {@link Player#PLAYER_1},
     * sets the game status to {@link Status#IN_PROGRESS}, and initializes
     * an empty game board of size 11x11.
     */
    public GameModel() {
        this.currentPlayer = Player.PLAYER_1;
        this.status = Status.IN_PROGRESS;
        this.board = new int[boardSize][boardSize];
    }

    /**
     * @return
     */
    @Override
    public Player getNextPlayer() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public boolean isGameOver() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public Status getStatus() {
        return null;
    }

    /**
     * @param player
     * @return
     */
    @Override
    public boolean isWinner(Player player) {
        return State.super.isWinner(player);
    }

    /**
     * @param o
     * @return
     */
    @Override
    public boolean isLegalMove(Object o) {
        return false;
    }

    /**
     * @param o
     */
    @Override
    public void makeMove(Object o) {

    }
}
