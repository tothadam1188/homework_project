package hu.unideb.inf.model;

import game.State;

public class GameModel implements State<Move>{

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
     * Returns the next player to make a move.
     * @return the {@link Player} who will play after the current player.
     */
    @Override
    public Player getNextPlayer() {
        if(currentPlayer==Player.PLAYER_1) return Player.PLAYER_2;
        return Player.PLAYER_1;
    }

    /**
     * @return
     */
    @Override
    public boolean isGameOver() {
        return false;
    }

    /**
     * Returns the current status of the game.
     * @return the {@link Status} representing the game's current state.
     */
    @Override
    public Status getStatus() {
        return status;
    }

    /**
     * Checks if the specified player has won the game.
     * @param player the {@link Player} to check for a winning condition
     * @return {@code true} if the given {@code player} has won, {@code false} otherwise
     */
    @Override
    public boolean isWinner(Player player) {
        return State.super.isWinner(player);
    }

    /**
     * Checks whether the specified move is within the bounds of the game board.
     * @param move the {@link Move} to validate
     * @return {@code true} if the {@code move} is inside the board boundaries, {@code false} otherwise
     */
    public boolean isLegalMove(Move move) {
        int row = move.row();
        int col = move.col();
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }

    /**
     * @param move
     */
    public void makeMove(Move move) {

    }
}
