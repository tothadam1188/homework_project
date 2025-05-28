package hu.unideb.inf.model;

import game.State;
import org.tinylog.Logger;

import java.util.Arrays;

/**
 * Game logic and state manager for the board game. Handles player turns, move validation, win conditions, and game status.
 * Implements {@link State} using {@link Move} for interactions.
 */
public class GameModel implements State<Move>{

    private Player player;
    private Status status;
    private static final int boardSize=11;
    private final int[][] board;

    /**
     * Initializes the game model with default settings.
     * This constructor sets the current player to {@link Player#PLAYER_1},
     * sets the game status to {@link Status#IN_PROGRESS}, and initializes
     * an empty game board of size 11x11.
     */
    public GameModel() {
        this.player = Player.PLAYER_1;
        this.status = Status.IN_PROGRESS;
        this.board = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (i%2==0 && j%2==1){
                    board[i][j]=1;
                }
                if(i%2==1 && j%2==0){
                    board[i][j]=2;
                }
            }
        }
        Logger.info("GameModel initialized with player={}, status={}", player, status);
    }

    /**
     * Returns the next player to make a move.
     * @return the {@link Player} who moves next.
     */
    @Override
    public Player getNextPlayer() {
        return player;
    }

    /**
     * Checks whether the game is over by evaluating win conditions for both players.
     * The game ends if:
     * <ul>
     *     <li>{@link Player#PLAYER_1} (value {@code 1}) has a complete vertical column of their markers.</li>
     *     <li>{@link Player#PLAYER_2} (value {@code 2}) has a complete horizontal row of their markers.</li>
     * </ul>
     * If either condition is met, the {@link #status} is updated accordingly and {@code true} is returned.
     * @return {@code true} if a player has won, otherwise {@code false}
     */
    @Override
    public boolean isGameOver() {
        Logger.debug("Checking if the game is over.");
        for (int col = 0; col < boardSize; col++) {
            boolean isContinuous = true;
            for (int row = 0; row < boardSize; row++) {
                if (board[row][col] != 1) {
                    isContinuous = false;
                    break;
                }
            }
            if (isContinuous) {
                status = Status.PLAYER_1_WINS;
                Logger.info("Game over: PLAYER_1 wins");
                return true;
            }
        }
        for (int row = 0; row < boardSize; row++) {
            boolean isContinuous = true;
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] != 2) {
                    isContinuous = false;
                    break;
                }
            }
            if (isContinuous) {
                status = Status.PLAYER_2_WINS;
                Logger.info("Game over: PLAYER_2 wins");
                return true;
            }
        }
        Logger.debug("No winning state reached, game is continuing.");
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
        boolean isMoveLegal=row >= 0 && row < boardSize && col >= 0 && col < boardSize;
        Logger.debug("Move legality checked at ({}, {}): {}", move.row(), move.col(), isMoveLegal);
        return isMoveLegal;
    }

    /**
     * Makes a move on the board for the current player and switches turns.
     * @param move the {@link Move} to execute
     */
    public void makeMove(Move move) {
        Logger.debug("Attempting to make move: {}", move);
        if (!isLegalMove(move)) {
            Logger.error("Illegal move attempted at row={}, col={}", move.row(), move.col());
            throw new GameModelException("Illegal move at row " + move.row() + ", column " + move.col());
        }
        if(player==Player.PLAYER_1) {
            Logger.info("PLAYER_1 moved at ({}, {})", move.row(), move.col());
            board[move.col()][move.row()] = 1;
        }
        if(player==Player.PLAYER_2) {
            Logger.info("PLAYER_2 moved at ({}, {})", move.row(), move.col());
            board[move.col()][move.row()] = 2;
        }

        if (!isGameOver()) {
            player = (player == Player.PLAYER_1) ? Player.PLAYER_2 : Player.PLAYER_1;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
