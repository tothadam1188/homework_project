package hu.unideb.inf.model.core;

import game.State;
import hu.unideb.inf.model.exception.GameModelException;
import hu.unideb.inf.model.persistence.Round;
import hu.unideb.inf.model.persistence.RoundDataManager;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * Game logic and state manager for the board game. Handles player turns, move validation, win conditions, and game status.
 * Implements {@link State} using {@link Move} for interactions.
 */
public class GameModel implements State<Move>{

    public static final int BOARD_SIZE = 11;
    private static final int PLAYER_1_MARKER = 1;
    private static final int PLAYER_2_MARKER = 2;

    private final int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private final RoundDataManager roundDataManager = new RoundDataManager();

    private Player currentPlayer = Player.PLAYER_1;
    private Status status = Status.IN_PROGRESS;
    private String player1Name;
    private String player2Name;
    private int rounds = 0;

    /**
     * Initializes the game model with default settings.
     * This constructor sets the current player to {@link Player#PLAYER_1},
     * sets the game status to {@link Status#IN_PROGRESS}, and initializes
     * an empty game board of size 11x11.
     */
    public GameModel() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (row % 2 == 0 && col % 2 == 1) {
                    board[row][col] = PLAYER_1_MARKER;
                } else if (row % 2 == 1 && col % 2 == 0) {
                    board[row][col] = PLAYER_2_MARKER;
                }
            }
        }
        Logger.info("GameModel initialized with player={}, status={}", currentPlayer, status);
    }

    /**
     * Sets the name of the two players.
     * @param player1Name the name of Player 1
     * @param player2Name the name of Player 2
     */
    public void setPlayers(String player1Name, String player2Name) {
        this.player1Name=player1Name;
        this.player2Name=player2Name;
    }

    /**
     * Returns the next player to make a move.
     * @return the {@link Player} who moves next.
     */
    @Override
    public Player getNextPlayer() {
        return currentPlayer;
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
        for (int col = 0; col < BOARD_SIZE; col++) {
            boolean fullColumn = true;
            for (int row = 0; row < BOARD_SIZE; row++) {
                if (board[row][col] != PLAYER_1_MARKER) {
                    fullColumn = false;
                    break;
                }
            }
            if (fullColumn) {
                status = Status.PLAYER_1_WINS;
                Logger.info("Game over: PLAYER_1 wins");
                return true;
            }
        }
        for (int row = 0; row < BOARD_SIZE; row++) {
            boolean fullRow = true;
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] != PLAYER_2_MARKER) {
                    fullRow = false;
                    break;
                }
            }
            if (fullRow) {
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
        boolean isMoveLegal=row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
        Logger.debug("Move legality checked at ({}, {}): {}", move.row(), move.col(), isMoveLegal);
        return isMoveLegal;
    }

    /**
     * Makes a move on the board for the current player and switches turns.
     * When the game is over, saves round data using {@link RoundDataManager}.
     * @param move the {@link Move} to execute
     */
    public void makeMove(Move move) {
        Logger.debug("Attempting to make move: {}", move);
        rounds++;

        if (!isLegalMove(move)) {
            Logger.error("Illegal move attempted at row={}, col={}", move.row(), move.col());
            throw new GameModelException("Illegal move at row " + move.row() + ", column " + move.col());
        }
        if(currentPlayer==Player.PLAYER_1) {
            board[move.row()][move.col()] = PLAYER_1_MARKER;
        }
        if(currentPlayer==Player.PLAYER_2) {
            board[move.row()][move.col()] = PLAYER_2_MARKER;
        }

        Logger.info("{} moved at ({}, {})", currentPlayer, move.row(), move.col());

        if (isGameOver()) {
            this.saveRound();
        }
        else{
            if(currentPlayer==Player.PLAYER_1) currentPlayer=Player.PLAYER_2;
            else if(currentPlayer==Player.PLAYER_2) currentPlayer=Player.PLAYER_1;
        }
    }

    private void saveRound() {
        try {
            roundDataManager.saveRound(new Round(player1Name, player2Name, rounds, this.toString(), status));
        } catch (IOException e) {
            Logger.error("Failed to save round data", e);
            throw new RuntimeException("Error saving round", e);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns the value of the cell at the specified row and column on the game board.
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @return the value at the specified cell (0 for empty, 1 for Player 1, 2 for Player 2)
     * @throws GameModelException if the row or column is out of bounds
     */
    public int getBoardCell(int row, int col) throws GameModelException {
        if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
            throw new GameModelException("Row or column index out of bounds: row=" + row + ", col=" + col);
        }
        return board[row][col];
    }

}
